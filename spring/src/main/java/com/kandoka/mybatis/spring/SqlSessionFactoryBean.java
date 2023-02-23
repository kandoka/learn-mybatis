package com.kandoka.mybatis.spring;

import com.kandoka.mybatis.io.Resources;
import com.kandoka.mybatis.session.SqlSessionFactory;
import com.kandoka.mybatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author 小傅哥，微信：fustack
 * @description 会话工厂对象
 * @date 2022/7/6
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean {

    private SqlSessionFactory sqlSessionFactory;

    private String resource;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader(resource));
    }

    @Override
    public SqlSessionFactory getObject() throws Exception {
        if (sqlSessionFactory == null) {
            afterPropertiesSet();
        }
        return this.sqlSessionFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
