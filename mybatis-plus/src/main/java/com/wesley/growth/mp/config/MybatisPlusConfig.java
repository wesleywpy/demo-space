package com.wesley.growth.mp.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus配置
 *
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页拦截器
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    /**
     * 自动填充配置
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisMetaObjectHandler();
    }

    /**
     * 自定义SQL注入器
     * @return
     */
    @Bean
    public MySqlInjector customerSqlInjector() {
        return new MySqlInjector();
    }

    /**
     * 乐观锁 插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLoker() {
        return new OptimisticLockerInterceptor();
    }

}
