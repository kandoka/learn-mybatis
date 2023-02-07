package com.kandoka.mybatis.test.dao;

import com.kandoka.mybatis.binding.MapperProxyFactory;
import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.io.Resources;
import com.kandoka.mybatis.session.SqlSession;
import com.kandoka.mybatis.session.SqlSessionFactory;
import com.kandoka.mybatis.session.SqlSessionFactoryBuilder;
import com.kandoka.mybatis.session.defaults.DefaultSqlSessionFactory;
import com.kandoka.mybatis.test.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 11:07
 */
@Slf4j
public class ApiTest {

    @Test
    public void test_MapperProxyFactory() {
//        //1. register mappers
//        MapperRegistry registry = new MapperRegistry();
//        registry.addMappers("com.kandoka.mybatis.test.dao");
//
//        // 2. get Session from SqlSession
//        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        //3. get mapper instance
//        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//
//        // 4. 测试验证
//        String res = userDao.queryUserName("10001");
//        log.info("测试结果：{}", res);
    }

    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        String res = userDao.queryUserById(10001L);
        log.info("测试结果：{}", res);
    }
}
