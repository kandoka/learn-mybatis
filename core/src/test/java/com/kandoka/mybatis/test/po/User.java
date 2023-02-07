package com.kandoka.mybatis.test.po;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/7 11:22
 */
@Data
@ToString
public class User implements Serializable {
    private String id;
    private String code;          // 用户ID
    private String fullname;        // 头像
    private Date createdTime;        // 创建时间
    private Date updatedTime;        // 更新时间
}
