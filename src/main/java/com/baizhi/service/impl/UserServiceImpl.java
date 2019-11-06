package com.baizhi.service.impl;

import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.StarDao;
import com.baizhi.dao.UserDao;
import com.baizhi.dao.UserEchartsDao;
import com.baizhi.entity.Charts;
import com.baizhi.entity.Star;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.MD5Utils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StarDao starDao;
    @Autowired
    private UserEchartsDao echartsDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectUsersByStarId(Integer page,Integer rows,String starId) {
        Map<String,Object> map=new HashMap<>();
        User user=new User();
        user.setStarId(starId);
        RowBounds rowBounds=new RowBounds((page-1)*rows,rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        int totalCounts = userDao.selectCount(user);
        map.put("rows",users);
        map.put("page",page);
        int totalPage=totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        map.put("total",totalPage);
        map.put("records",totalCounts);
        return map;
    }

    @Override
    @RedisCache
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<>();
        User user=new User();
        RowBounds rowBounds=new RowBounds((page-1)*rows,rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        Star star=null;
        for (User user1 : users) {
            star = starDao.selectByPrimaryKey(user1.getStarId());
            user1.setStar(star);
        }
        int totalCounts = userDao.selectCount(user);
        map.put("rows",users);
        map.put("page",page);
        int totalPage=totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        map.put("total",totalPage);
        map.put("records",totalCounts);
        return map;
    }

    @Override
    @RedisCache
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public Integer[] select(String sex) {
        Integer[] count = new Integer[6];
        List<Charts> charts = echartsDao.select(sex);
        for (Charts chart : charts) {
            for(int i=0;i<count.length;i++){
                Integer month = chart.getMonth();
                if(month==i+1){
                    count[i]=chart.getCount();
                }
            }
        }
        return count;
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        //生成盐
        String salt = MD5Utils.getSalt();
        user.setSalt(salt);
        //将输入的密码+随机盐进行MD5加密
        String password = user.getPassword();
        String secretPassword = MD5Utils.getPassword(password+salt);
        //将密文入库
        user.setPassword(secretPassword);
        user.setCreateDate(new Date());
        userDao.insertSelective(user);
    }
}
