package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> selectUsersByStarId(Integer page,Integer rows,String starId);
    Map<String,Object> selectAll(Integer page,Integer rows);
    List<User> selectAll();

    Integer[] select(String sex);

    void save(User user);
}
