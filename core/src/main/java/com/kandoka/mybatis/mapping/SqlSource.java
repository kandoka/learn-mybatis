package com.kandoka.mybatis.mapping;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/14 17:46
 */
public interface SqlSource {
    BoundSql getBoundSql(Object parameterObject);
}
