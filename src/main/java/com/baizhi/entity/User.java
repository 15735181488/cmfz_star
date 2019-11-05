package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class User implements Serializable {
    @Id
    @Excel(name="编号")
    private String id;
    @Excel(name="用户名")
    private String username;
    private String password;
    private String salt;
    @Excel(name="昵称")
    private String nickname;
    @Excel(name="性别")
    private String sex;
    @Excel(name = "头像",type = 2)
    private String photo;
    @Excel(name="省份")
    private String province;
    @Excel(name="城市")
    private String city;
    @Excel(name="电话")
    private String phone;
    @Excel(name="标签")
    private String sign;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册日期",format = "yyyy-MM-dd")
    private Date createDate;
    private String starId;
    @ExcelEntity
    private Star star;


}
