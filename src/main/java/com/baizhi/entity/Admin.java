package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
//@Table(name = "t_admin") 实体和表的映射
public class Admin implements Serializable {
    @Id
    private String id;
    private String username;
    private String password;
    private String salt;
    private String nickname;
    private String phone;

}
