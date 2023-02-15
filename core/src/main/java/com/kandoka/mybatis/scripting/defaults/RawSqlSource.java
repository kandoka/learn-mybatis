package com.kandoka.mybatis.scripting.defaults;

import com.kandoka.mybatis.builder.SqlSourceBuilder;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.SqlSource;
import com.kandoka.mybatis.scripting.xmltags.DynamicContext;
import com.kandoka.mybatis.scripting.xmltags.SqlNode;
import com.kandoka.mybatis.session.Configuration;

import java.util.HashMap;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/14 17:23
 */
public class RawSqlSource implements SqlSource {

    private final SqlSource sqlSource;

    public RawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
        this(configuration, getSql(configuration, rootSqlNode), parameterType);
    }

    public RawSqlSource(Configuration configuration, String sql, Class<?> parameterType) {
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> clazz = parameterType == null ? Object.class : parameterType;
        sqlSource = sqlSourceParser.parse(sql, clazz, new HashMap<>());
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return sqlSource.getBoundSql(parameterObject);
    }

    private static String getSql(Configuration configuration, SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(configuration, null);
        rootSqlNode.apply(context);
        return context.getSql();
    }
}
