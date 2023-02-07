package com.kandoka.mybatis.builder;

import com.kandoka.mybatis.session.Configuration;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 17:56
 */
public class BaseBuilder {

    protected final  Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
}
