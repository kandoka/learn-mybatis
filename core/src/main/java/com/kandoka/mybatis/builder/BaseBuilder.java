package com.kandoka.mybatis.builder;

import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.type.TypeAliasRegistry;
import com.kandoka.mybatis.type.TypeHandlerRegistry;

/**
 * @Description Base builder
 * @Author kandoka
 * @Date 2023/2/6 17:56
 */
public class BaseBuilder {

    private static final MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.CONFIG, BaseBuilder.class);

    protected final  Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;
    protected final TypeHandlerRegistry typeHandlerRegistry;


    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    }

    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}
