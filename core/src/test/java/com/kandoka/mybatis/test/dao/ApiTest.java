package com.kandoka.mybatis.test.dao;

import com.kandoka.mybatis.binding.MapperProxyFactory;
import com.kandoka.mybatis.binding.MapperRegistry;
import com.kandoka.mybatis.session.SqlSession;
import com.kandoka.mybatis.session.SqlSessionFactory;
import com.kandoka.mybatis.session.defaults.DefaultSqlSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
        //1. register mappers
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("com.kandoka.mybatis.test.dao");

        // 2. get Session from SqlSession
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //3. get mapper instance
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 4. 测试验证
        String res = userDao.queryUserName("10001");
        log.info("测试结果：{}", res);
    }
}
