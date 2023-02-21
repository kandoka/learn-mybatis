package com.kandoka.mybatis.mapping;

import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.type.JdbcType;
import com.kandoka.mybatis.type.TypeHandler;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/20 18:02
 */
public class ResultMapping {

    private Configuration configuration;
    private String property;
    private String column;
    private Class<?> javaType;
    private JdbcType jdbcType;
    private TypeHandler<?> typeHandler;

    ResultMapping() {
    }

    public static class Builder {
        private ResultMapping resultMapping = new ResultMapping();


    }
}
