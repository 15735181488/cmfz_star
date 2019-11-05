package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BannerService {

    List<Banner> findAll(Integer page,Integer rows);
    int findTotalCounts();
    String add(Banner banner);
    void update(Banner banner);
    void delete(String id, HttpServletRequest request);
}
