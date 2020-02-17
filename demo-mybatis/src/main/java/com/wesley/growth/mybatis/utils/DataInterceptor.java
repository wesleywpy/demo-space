package com.wesley.growth.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * <p>
 * 数据权限拦截
 * </p>
 * Email yani@uoko.com
 *
 * @author Created by Yani on 2019/06/14
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        }
)
@Slf4j
public class DataInterceptor implements Interceptor {

    private static final String PERMISSION_FIELD = "_housing_id";

    private String dbType;

//    private RedisTemplate redisTemplate;

    public DataInterceptor(String dbType) {
        this.dbType = dbType;
//        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if(args.length == 4){
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        String permissionField = checkTargetMethod(ms);
        if (Objects.isNull(permissionField)) {
            if (log.isDebugEnabled()) {
                log.info("----- {} : 不进行数据权限拦截 -----", ms.getId());
            }
            return invocation.proceed();
        }

        // 获取当前用户信息里面的权限
//        if (StringUtils.isAnyBlank(user.getUserId(), user.getCompanyId())) {
            if (log.isDebugEnabled()) {
                log.info("----- {} : 无用户信息 不进行数据权限拦截 -----", ms.getId());
            }
//            return invocation.proceed();
//        }

        // 从缓存中获取用户权限
//        String key = String.format(CommonConstants.REDIS_USER_ASSETS, user.getCompanyId(), user.getUserId());
        String userCache = "redis Cache";
//        if (StringUtils.isBlank(userCache)) {
            if (log.isDebugEnabled()) {
                log.info("----- {} : 未找到权限数据 不进行数据权限拦截 -----", ms.getId());
            }
//            return invocation.proceed();
//        }

//        UserDTO userDto = JSONObject.parseObject(userCache, UserDTO.class);
        // 构建Sql
        //buildSql(boundSql.getSql(), permissionField, userDto.getAssets());
        String permissionSql = "";
        if (log.isDebugEnabled()) {
            log.debug("数据权限Sql ---> ", permissionSql);
        }

        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, permissionSql);

        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    /**
     * 检查方法是否需要进行 数据权限访问控制
     * @return 返回null 不进行拦截
     */
    private String checkTargetMethod(MappedStatement mappedStatement) {
        String namespace = mappedStatement.getId();
        int lastIndex = namespace.lastIndexOf(".");
        String className = namespace.substring(0, lastIndex);
        String methedName = namespace.substring(lastIndex + 1);
        try {
            Method[] methods = Class.forName(className).getMethods();
            for(Method m : methods){
                if(m.getName().equals(methedName)){

                    return PERMISSION_FIELD;
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("数据权限拦截 解析Mapper方法出错", e);
        }

        return null;
    }

    /**
     * 拼接Sql
     */
    private String buildSql(String sql, String field, List<Object> assets) {
        return "";
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String type = properties.getProperty("dbType");
        if (StringUtils.isNotBlank(type)) {
            this.dbType = type;
        }
    }
}
