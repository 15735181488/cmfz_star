package com.baizhi.dao;

import com.baizhi.entity.Charts;

import java.util.List;

public interface UserEchartsDao {
    List<Charts> select(String sex);
}
