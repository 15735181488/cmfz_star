package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
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
        admin.setId(UUID.randomUUID().toString());
        adminDao.insertSelective(admin);
    }
}
