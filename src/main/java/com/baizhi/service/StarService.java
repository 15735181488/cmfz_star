package com.baizhi.service;

import com.baizhi.entity.Star;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StarService {
    List<Star> selectAll(Integer page,Integer rows);
    int findTotalCounts();
    String add(Star star);
    void update(Star star);
    void delete(String id, HttpServletRequest request);
    List<Star> findAll();

}
