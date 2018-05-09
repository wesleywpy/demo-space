package com.wesley.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author Created by Wesley on 2018/4/30.
 */
public class CustomRealmTest {

    @Test
    public void authenticate(){
        CustomRealm customRealm = new CustomRealm();
        //1. 构建SecurityManger环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(customRealm);

        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        customRealm.setCredentialsMatcher(credentialsMatcher);

        //2. 主题提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("wesley", "123456");
        subject.login(token);

        System.out.println("isAuthenticated  -> "+ subject.isAuthenticated());

        //验证角色
        subject.checkRole("admin");
        //检查权限
        subject.checkPermission("user:add");
    }
}
