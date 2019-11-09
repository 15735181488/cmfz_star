package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

@SpringBootTest
class CmfzStarApplicationTests {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private RedisTemplate redisTemplate;

    //可以做键绑定，使用起来更简单一些
    @Test
    public void testList2() {
        BoundListOperations ops = redisTemplate.boundListOps("aa");
        ops.leftPush("123");
        //ops.leftPush(new Admin("2", "huahua", "222", "huu", "12345"));

        List list = ops.range(0, -1);
        for (Object o : list) {
            System.out.println(o);
        }
    }


    @Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("aa", "123");
        //listOperations.leftPush("aa", new Admin("1", "huahua", "111", "huu", "12345"));

        List list = listOperations.range("aa", 0, -1);
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void testString() {

        ValueOperations valueOperations = redisTemplate.opsForValue();

        valueOperations.set("name", "zhangsan");
        //valueOperations.set("admin", new Admin("1", "huahua", "111", "huu", "12345"));

        Object name = valueOperations.get("name");
        System.out.println(name);
        Admin admin = (Admin) valueOperations.get("admin");
        System.out.println(admin.getNickname());
    }


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
