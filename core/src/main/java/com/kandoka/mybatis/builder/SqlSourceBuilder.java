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
        // θΏειζ SQL
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
         * θΏδΈι¨εε°±ζ―ε―Ήεζ°ηη»εε€ηοΌζε»ΊεΊεζ°ηζ ε°ε³η³»οΌι¦εζ― if ε€ζ­ε―ΉεΊηεζ°η±»εζ―ε¦ε¨
         * TypeHandlerRegistry ζ³¨εε¨δΈ­οΌε¦ζδΈε¨εζθ§£ε―Ήθ±‘οΌζε±ζ§θΏθ‘θ·ε propertyType ηζδ½γ
         * @param content
         * @return
         */
        private ParameterMapping buildParameterMapping(String content) {
            // εθ§£ζεζ°ζ ε°,ε°±ζ―θ½¬εζδΈδΈͺ HashMap | #{favouriteSection,jdbcType=VARCHAR}
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
