package com.kandoka.mybatis.test.dao;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 11:07
 */
public interface IUserDao {
    String queryUserName(String userId);
    Integer queryUserAge(String userId);
}
