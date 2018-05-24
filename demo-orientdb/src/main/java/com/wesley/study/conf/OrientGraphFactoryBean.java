package com.wesley.study.conf;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by Wesley on 2017/4/3.
 */
@Configuration
public class OrientGraphFactoryBean {

    @Autowired
    OrientDBConfig orientDBConfig;

    /**
     * @return 默认配置的OrientGraphFactory
     */
    @Bean
    public OrientGraphFactory build(){
        return new OrientGraphFactory(orientDBConfig.getUrl(), orientDBConfig.getUser(), orientDBConfig.getPassword());
    }

}
