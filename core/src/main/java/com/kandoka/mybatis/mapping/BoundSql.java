package com.kandoka.mybatis.mapping;

import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.reflection.MetaObject;
import com.kandoka.mybatis.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description Sql properties that bounded to a statement. As a final product after parsing a sql.
 * @Author kandoka
 * @Date 2023/2/7 15:22
 */
public class BoundSql {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.SQL, BoundSql.class);

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;
    private Map<String, Object> additionalParameters;
    private MetaObject metaParameters;

    public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        log.info("Create a bound for sql: {}, including its parameter mappings and actual values", sql);
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<>();
        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public boolean hasAdditionalParameter(String name) {
        return metaParameters.hasGetter(name);
    }

    public void setAdditionalParameter(String name, Object value) {
        metaParameters.setValue(name, value);
    }

    public Object getAdditionalParameter(String name) {
        return metaParameters.getValue(name);
    }
}
