package com.kandoka.mybatis.scripting;

import com.kandoka.mybatis.executor.resultset.ParameterHandler;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.mapping.SqlSource;
import com.kandoka.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/14 17:25
 */
public interface LanguageDriver {

    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);

    /**
     * 创建参数处理器
     */
    ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);

}
