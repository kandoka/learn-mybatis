package com.kandoka.mybatis.builder;

import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.ParameterMapping;
import com.kandoka.mybatis.mapping.SqlSource;
import com.kandoka.mybatis.parsing.GenericTokenParser;
import com.kandoka.mybatis.parsing.TokenHandler;
import com.kandoka.mybatis.reflection.MetaClass;
import com.kandoka.mybatis.reflection.MetaObject;
import com.kandoka.mybatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description sql builder, parsing tokens sun as "${}", and mapping parameter to tokens
 * @Author kandoka
 * @Date 2023/2/14 17:13
 */
public class SqlSourceBuilder extends BaseBuilder {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.SQL, SqlSourceBuilder.class);

    private static final String parameterProperties = "javaType,jdbcType,mode,numericScale,resultMap,typeHandler,jdbcTypeName";

    public SqlSourceBuilder(Configuration configuration) {
        super(configuration);
        log.info("create a sql source builder");
    }

    public SqlSource parse(String originalSql, Class<?> parameterType, Map<String, Object> additionalParameters) {
        log.info("Parse token like '#\\{\\}' in sql: {}", originalSql);
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(configuration, parameterType, additionalParameters);
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String sql = parser.parse(originalSql);
        // 返回静态 SQL
        return new StaticSqlSource(configuration, sql, handler.getParameterMappings());
    }

    private static class ParameterMappingTokenHandler extends BaseBuilder implements TokenHandler {

        private List<ParameterMapping> parameterMappings = new ArrayList<>();
        private Class<?> parameterType;
        private MetaObject metaParameters;

        public ParameterMappingTokenHandler(Configuration configuration, Class<?> parameterType, Map<String, Object> additionalParameters) {
            super(configuration);
            log.info("start building token handler");
            this.parameterType = parameterType;
            this.metaParameters = configuration.newMetaObject(additionalParameters);
        }

        public List<ParameterMapping> getParameterMappings() {
            return parameterMappings;
        }

        @Override
        public String handleToken(String content) {
            parameterMappings.add(buildParameterMapping(content));
            return "?";
        }

        /**
         * 这一部分就是对参数的细化处理，构建出参数的映射关系，首先是 if 判断对应的参数类型是否在
         * TypeHandlerRegistry 注册器中，如果不在则拆解对象，按属性进行获取 propertyType 的操作。
         * @param content
         * @return
         */
        private ParameterMapping buildParameterMapping(String content) {
            // 先解析参数映射,就是转化成一个 HashMap | #{favouriteSection,jdbcType=VARCHAR}
            Map<String, String> propertiesMap = new ParameterExpression(content);
            String property = propertiesMap.get("property");

            Class<?> propertyType;
            if (typeHandlerRegistry.hasTypeHandler(parameterType)) {
                propertyType = this.parameterType;
            } else if (property != null) {
                MetaClass metaClass = MetaClass.forClass(parameterType);
                if (metaClass.hasGetter(property)) {
                    propertyType = metaClass.getGetterType(property);
                } else {
                    propertyType = Object.class;
                }
            } else {
                propertyType = Object.class;
            }

            ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration, property, propertyType);
            return builder.build();
        }

    }
}
