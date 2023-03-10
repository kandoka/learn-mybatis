package com.kandoka.mybatis.test;

import com.kandoka.mybatis.io.Resources;
import com.kandoka.mybatis.session.SqlSession;
import com.kandoka.mybatis.session.SqlSessionFactory;
import com.kandoka.mybatis.session.SqlSessionFactoryBuilder;
import com.kandoka.mybatis.test.dao.IUserDao;
import com.kandoka.mybatis.test.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 11:07
 */
@Slf4j
public class ApiTest {

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource.xml"));
        sqlSession = sqlSessionFactory.openSession();
    }

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
//        // 1. 从SqlSessionFactory中获取SqlSession
//        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        // 2. 获取映射器对象
//        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//
//        // 3. 测试验证
//        String res = userDao.queryUserById(10001L);
//        log.info("测试结果：{}", res);
    }

    @Test
    public void test_SqlSessionFactoryWithDatasource() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        User user = userDao.queryUserById(1L);
        log.info("测试结果：{}", user);
    }

    @Test
    public void test_SqlSessionFactoryWithPoolAndUnpooledDatasource() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            User user = userDao.queryUserById(1L);
        }
        long end = System.currentTimeMillis();
        log.info("测试结果：{} ms", end - start);
    }

    @Test
    public void test_prarameterHandler() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        long start = System.currentTimeMillis();
        User queryPram = new User();
        queryPram.setId("1");
        queryPram.setCode("admin");
        User user = userDao.queryUser(queryPram);
        long end = System.currentTimeMillis();
        log.info("测试结果：{} ms，{}", end - start, user);
    }

    @Test
    public void test_insertUserInfo() {
        // 1. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 2. 测试验证
        long start = System.currentTimeMillis();
        User user = new User();
        user.setCode("10002");
        user.setFullname("小白");
        userDao.insert(user);
        // 3. 提交事务
        sqlSession.commit();
        long end = System.currentTimeMillis();
        log.info("测试结果：{} ms，{}", end - start, user);
    }

    @Test
    public void test_listUserInfo() {
        // 1. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 2. 测试验证
        long start = System.currentTimeMillis();

        List<User> users = userDao.list();
        long end = System.currentTimeMillis();
        log.info("测试结果：{} ms，{}", end - start, users);
    }
}
