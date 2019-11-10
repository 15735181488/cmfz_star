package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.AdminRoleDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminRole;
import com.baizhi.service.AdminService;
import com.baizhi.util.MD5Utils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public void login(Admin admin, String inputCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");
        //忽略大小写
        if(securityCode.equalsIgnoreCase(inputCode)){
            Admin loginAdmin = adminDao.selectOne(admin);
            if(loginAdmin!=null){
                session.setAttribute("loginAdmin",loginAdmin);
            }else{
                throw new RuntimeException("用户名或密码错误!!!");
            }
        }else{
            throw new RuntimeException("验证码错误!!!");
        }
    }

    @Override
    public void save(Admin admin) {
        String id = UUID.randomUUID().toString();
        admin.setId(id);
        //生成盐
        String salt = MD5Utils.getSalt();
        admin.setSalt(salt);
        //将输入的密码+随机盐进行加密
        String password = admin.getPassword();
        Md5Hash hash = new Md5Hash(password, salt, 1024);
        String secretPassword = hash.toHex();
        //将密文入库
        admin.setPassword(secretPassword);
        AdminRole adminRole = new AdminRole();
        adminRole.setId(UUID.randomUUID().toString());
        adminRole.setAdminId(id);
        adminRole.setRoleId("r1");
        adminRoleDao.insertSelective(adminRole);
        adminDao.insertSelective(admin);
    }
}
