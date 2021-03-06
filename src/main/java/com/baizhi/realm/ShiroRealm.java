package com.baizhi.realm;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.AdminRoleDao;
import com.baizhi.dao.RoleDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminRole;
import com.baizhi.entity.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminRoleDao adminRoleDao;
    @Autowired
    private RoleDao roleDao;

    //    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = adminDao.selectOne(new Admin(null, username, null, null, null, null));
        List<AdminRole> list = adminRoleDao.select(new AdminRole(null, admin.getId(), null));
        ArrayList<String> list1 = new ArrayList<>();
        for (AdminRole adminRole : list) {
            Role role = roleDao.selectOne(new Role(adminRole.getRoleId(), null));
            list1.add(role.getName());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(list1);
        if ("zhangsan".equals(username)) {
            info.addStringPermission("user:select");
        }
        if ("lisi".equals(username)) {
            info.addStringPermission("user:*");
        }
        if ("zzj".equals(username)) {
            info.addStringPermission("user:*");
        }
        return info;
    }

    //    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Admin admin = new Admin();
        admin.setUsername(token.getUsername());
        Admin loginAdmin = adminDao.selectOne(admin);
        if (loginAdmin == null) {
            return null;
        } else {
            SimpleAccount account = new SimpleAccount(loginAdmin.getUsername(), loginAdmin.getPassword(), ByteSource.Util.bytes(loginAdmin.getSalt()), "com.baizhi.realm.ShiroRealm");
            return account;
        }
    }
}
