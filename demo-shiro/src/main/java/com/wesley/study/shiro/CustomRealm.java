package com.wesley.study.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Created by Wesley on 2018/5/1.
 */
public class CustomRealm  extends AuthorizingRealm {

    private static final String CREDENTIAL_SALT = "WesleyWong";

    private Map<String, String> users;

    {
        users = new HashMap<>();
        users.put("wesley", generatePassword("123456"));
    }

    /**
     * 获取授权信息
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        Set<String> roles = getRolesByUsername(username);
        Set<String> permissions = getPermissionsByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 模拟从数据库或缓存中获取角色信息
     */
    private Set<String> getRolesByUsername(String username) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        return roles;
    }

    /**
     * 模拟从数据库或缓存中获取权限
     */
    private Set<String> getPermissionsByUsername(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:add");
        return permissions;
    }


    /**
     * 获取认证信息
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = getPasswordByUserName(username);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(CREDENTIAL_SALT));
        return authenticationInfo;
    }

    /**
     * 模拟从数据库中获取密码
     */
    private String getPasswordByUserName(String username) {
        return users.get(username);
    }

    /**
     * 生成密文
     */
    private String generatePassword(String password){
        Md5Hash md5Hash = new Md5Hash(password, CREDENTIAL_SALT);
        return md5Hash.toString();
    }

}
