package com.kandoka.mybatis.mapping;

import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.type.JdbcType;
import com.kandoka.mybatis.type.TypeHandler;
import com.kandoka.mybatis.type.TypeHandlerRegistry;

/**
 * @Description A mapping for sql parameter among its java type, jdbc type and type handler
 * @Author kandoka
 * @Date 2023/2/7 15:23
 */
public class ParameterMapping {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.PARAMETER, ParameterMapping.class);

    private Configuration configuration;

    // property
    private String property;
    // javaType = int
    private Class<?> javaType = Object.class;
    // jdbcType=NUMERIC
    private JdbcType jdbcType;
    //type handler of this type
    private TypeHandler<?> typeHandler;

    private ParameterMapping() {
    }

    public static class Builder {

        private ParameterMapping parameterMapping = new ParameterMapping();

        public Builder(Configuration configuration, String property, Class<?> javaType) {
            log.info("Create a builder for property: {}, its type is: {}", property, javaType.getCanonicalName());
            parameterMapping.configuration = configuration;
            parameterMapping.property = property;
            parameterMapping.javaType = javaType;
        }

        public Builder javaType(Class<?> javaType) {
            parameterMapping.javaType = javaType;
            return this;
        }

        public Builder jdbcType(JdbcType jdbcType) {
            parameterMapping.jdbcType = jdbcType;
            return this;
        }

        public ParameterMapping build() {
            //set a type handler for the parameter
            if (parameterMapping.typeHandler == null && parameterMapping.javaType != null) {
                Configuration configuration = parameterMapping.configuration;
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                log.info("Set type handler where java type is: {}, jdbc type is: {}", parameterMapping.javaType, parameterMapping.jdbcType);
                parameterMapping.typeHandler = typeHandlerRegistry.getTypeHandler(parameterMapping.javaType, parameterMapping.jdbcType);
            }

            return parameterMapping;
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getProperty() {
        return property;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }
}
