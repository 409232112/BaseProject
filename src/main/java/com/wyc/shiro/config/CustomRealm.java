package com.wyc.shiro.config;
import com.mysql.cj.conf.ConnectionUrlParser;
import com.wyc.shiro.CurrentUserHelper;
import com.wyc.systemmgr.entity.User;
import com.wyc.systemmgr.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangyc on 2019/11/11.
 */
public class CustomRealm extends AuthorizingRealm  {
    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        roles.add(CurrentUserHelper.getRole());
        /**
        Set<String> stringSet = new HashSet<>();
        stringSet.add("user:show");
        stringSet.add("user:admin");
        info.setStringPermissions(stringSet);
         **/
        info.setRoles(roles);
        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        Map user = userService.findUserByUsername(userName);
        if (user == null) {
            throw new AccountException("用户名不正确");
        } else if (!userPwd.equals(String.valueOf(user.get("password")))) {
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(user, userPwd,getName());
    }
}
