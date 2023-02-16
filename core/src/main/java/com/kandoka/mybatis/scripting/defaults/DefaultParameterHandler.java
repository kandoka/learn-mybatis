package com.kandoka.mybatis.scripting.defaults;

import com.alibaba.fastjson.JSON;
import com.kandoka.mybatis.executor.resultset.ParameterHandler;
import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.mapping.ParameterMapping;
import com.kandoka.mybatis.reflection.MetaObject;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.type.JdbcType;
import com.kandoka.mybatis.type.TypeHandler;
import com.kandoka.mybatis.type.TypeHandlerRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/14 17:23
 */
public class DefaultParameterHandler implements ParameterHandler {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.PARAMETER, DefaultParameterHandler.class);

    private final TypeHandlerRegistry typeHandlerRegistry;

    private final MappedStatement mappedStatement;
    private final Object parameterObject;
    private BoundSql boundSql;
    private Configuration configuration;

    public DefaultParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        log.info("Create a parameter handler for sql: {}, \nparameter object: {}", boundSql.getSql(), parameterObject);
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
        this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
        this.parameterObject = parameterObject;
        this.boundSql = boundSql;
    }

    @Override
    public Object getParameterObject() {
        return parameterObject;
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (null != parameterMappings) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String propertyName = parameterMapping.getProperty();
                Object value;
                if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    // 通过 MetaObject.getValue 反射取得值设进去
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
                JdbcType jdbcType = parameterMapping.getJdbcType();

                // 设置参数
                log.info("根据每个ParameterMapping中的TypeHandler设置对应的参数信息 property: {}, value：{}",
                        propertyName, JSON.toJSONString(value));
                TypeHandler typeHandler = parameterMapping.getTypeHandler();
                typeHandler.setParameter(ps, i + 1, value, jdbcType);
            }
        }
    }

}
