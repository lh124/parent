package io.yfjz.shiro;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import io.yfjz.entity.sys.SysMenuEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysMenuService;
import io.yfjz.service.sys.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    public UserRealm() {
    }

    /**
     * @param [principals]
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @describe: 权限认证
     * @method_name: doGetAuthorizationInfo
     * @author 刘琪
     * @QQ： 1018628825@qq.com
     * @tel: 15685413726
     * @date: 2017-12-06  11:42
     **/
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取系统用户实体对象
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        String userId = user.getUserId();
        List permsList;
        Iterator ite;
        if (user.getType() == 1) {//默认管理员
            List permsSet = this.sysMenuService.queryList(new HashMap());
            permsList = new ArrayList(permsSet.size());
            ite = permsSet.iterator();
            while (ite.hasNext()) {
                //不要频繁创建Java对象
                //SysMenuEntity info = (SysMenuEntity) ite.next();
                permsList.add(((SysMenuEntity) ite.next()).getPerms());
            }
        } else {
            permsList = this.sysUserService.queryAllPerms(userId);
        }

        HashSet permsSet1 = new HashSet();
        ite = permsList.iterator();
        while (ite.hasNext()) {
            String info1 = (String) ite.next();
            if (!StringUtils.isBlank(info1)) {
                permsSet1.addAll(Arrays.asList(info1.trim().split(",")));
            }
        }
        SimpleAuthorizationInfo info2 = new SimpleAuthorizationInfo();
        info2.setStringPermissions(permsSet1);
        return info2;
    }

    /**
     * @param [token]
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @describe: 身份认证
     * @method_name: doGetAuthenticationInfo
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-06  11:41
     **/
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        SysUserEntity user = this.sysUserService.queryByUserName(username);
        if (user == null) {
            throw new UnknownAccountException("用户未查询");
        } else if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        } else if (user.getStatus().intValue() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }else {
            /*
             * 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
             */
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, this.getName());
            return info;
        }
    }
}
