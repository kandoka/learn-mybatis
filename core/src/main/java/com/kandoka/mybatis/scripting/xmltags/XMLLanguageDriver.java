package com.kandoka.mybatis.scripting.xmltags;

import com.kandoka.mybatis.executor.parameter.ParameterHandler;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.mapping.SqlSource;
import com.kandoka.mybatis.scripting.LanguageDriver;
import com.kandoka.mybatis.scripting.defaults.DefaultParameterHandler;
import com.kandoka.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/14 17:24
 */
public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }
}
