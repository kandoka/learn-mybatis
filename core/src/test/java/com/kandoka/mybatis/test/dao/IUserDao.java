package com.kandoka.mybatis.test.dao;

import com.kandoka.mybatis.test.po.User;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 11:07
 */
public interface IUserDao {
//    String queryUserName(String userId);
//    Integer queryUserAge(String userId);

    User queryUserById(Long id);

    User queryUser(User user);
}
