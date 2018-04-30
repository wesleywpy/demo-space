package com.wesley.study.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author Created by Wesley on 2018/4/30.
 */
public class JdbcRealmTest {

    private DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/wesley");
        dataSource.setUsername("root");
        dataSource.setPassword("root123");

    }

    @Test
    public void authenticate(){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);

        //查询语句
        jdbcRealm.setAuthenticationQuery("select password from shiro_users where username = ?");
        jdbcRealm.setUserRolesQuery("select role_name from shiro_user_roles where username = ?");
        jdbcRealm.setPermissionsQuery("select permission from shiro_roles_permissions where role_name = ?");
        jdbcRealm.setPermissionsLookupEnabled(true); //权限查询打开

        //1. 构建SecurityManger环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(jdbcRealm);

        //2. 主题提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("wesley", "123456");
        subject.login(token);

        System.out.println("isAuthenticated  -> "+ subject.isAuthenticated());

        //验证角色
        subject.checkRole("admin");
        //检查权限
        subject.checkPermission("user:update");
    }
}
