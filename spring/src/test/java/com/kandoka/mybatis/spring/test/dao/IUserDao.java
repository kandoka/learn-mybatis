package com.kandoka.mybatis.spring.test.dao;

import com.kandoka.mybatis.spring.test.po.User;

import java.util.List;

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

    List<User> list();

    void insert(User user);

    int update(User user);

    int delete(String id);
}
