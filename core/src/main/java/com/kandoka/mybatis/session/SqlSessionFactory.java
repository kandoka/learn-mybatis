package com.kandoka.mybatis.session;

/**
 * @Description 是一个简单工厂模式，用于提供 SqlSession 服务，屏蔽创建细节，延迟创建过程。
 * @Author kandoka
 * @Date 2023/2/6 16:37
 */
public interface SqlSessionFactory {

    /**
     * open a session
     * @return SqlSession
     */
    SqlSession openSession();
}
