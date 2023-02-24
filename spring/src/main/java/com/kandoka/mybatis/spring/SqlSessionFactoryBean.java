package com.kandoka.mybatis.spring;

import com.kandoka.mybatis.io.Resources;
import com.kandoka.mybatis.session.SqlSessionFactory;
import com.kandoka.mybatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author
 * @description 会话工厂对象
 * @date 2022/7/6
 * @github https://github.com/fuzhengwei
 * @Copyright
 */
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean {

    private SqlSessionFactory sqlSessionFactory;

    //从spring.xml注入的属性
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

    public void setResource(String resource) {
        this.resource = resource;
    }
}
