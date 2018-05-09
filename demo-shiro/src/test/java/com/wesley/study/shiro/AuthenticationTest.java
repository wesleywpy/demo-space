package com.wesley.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Created by Wesley on 2018/4/29.
 */
public class AuthenticationTest {

    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void setup(){
        simpleAccountRealm.addAccount("wesley", "123456", "admin");
    }

    @Test
    public void authenticate(){
        //1. 构建SecurityManger环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(simpleAccountRealm);

        //2. 主题提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("wesley", "123456");
        subject.login(token);

        System.out.println("isAuthenticated  -> "+ subject.isAuthenticated());

        //验证角色
        subject.checkRole("admin");
    }
}
