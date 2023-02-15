package com.kandoka.mybatis.builder;

import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.type.TypeAliasRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description Base builder
 * @Author kandoka
 * @Date 2023/2/6 17:56
 */
public class BaseBuilder {

    private static final MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.BUILD, BaseBuilder.class);

    protected final  Configuration configuration;

    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        log.info("start building config via resources");
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }

    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}
