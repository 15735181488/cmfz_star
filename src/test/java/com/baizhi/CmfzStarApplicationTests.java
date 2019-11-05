package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmfzStarApplicationTests {
    @Autowired
    private AdminDao adminDao;

    @Test
    void contextLoads() {
       /* List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }*/

       /* Admin admin = new Admin();
      admin.setUsername("zhangsan");
       admin.setPassword("111111");
       List<Admin> list = adminDao.select(admin);
        for (Admin admin1 : list) {
            System.out.println(admin1);
       }*/


        Admin admin = adminDao.selectByPrimaryKey("1");
       System.out.println(admin);


    }

}
