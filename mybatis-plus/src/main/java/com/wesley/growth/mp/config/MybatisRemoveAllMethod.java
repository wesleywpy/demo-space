package com.wesley.growth.mp.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * <p>
 *  自定义Sql方法
 * </p>
 *
 * @author Created by Wesley on 2020/09/07
 */
public class MybatisRemoveAllMethod extends AbstractMethod{

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 执行的SQL
        String sql = "delete from " + tableInfo.getTableName();
        // Mapper接口方法名称
        String method = "deleteAll";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addDeleteMappedStatement(mapperClass, method, sqlSource);
    }


}
