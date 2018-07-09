package com.wesley.study.conf;

import com.wesley.study.transaction.OrientDBGraphFactory;
import com.wesley.study.transaction.OrientDBTransactionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Created by Wesley on 2017/4/3.
 */
@Configuration
@ConfigurationProperties(prefix = "orient.database")
public class OrientDBConfig {

    private String url;

    private String user;

    private String password;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bean
    public OrientDBGraphFactory orientDBGraphFactoryBean() {
        return new OrientDBGraphFactory(this.url, this.user, this.password);
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        return new OrientDBTransactionManager();
    }
}
