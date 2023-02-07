package com.kandoka.mybatis.builder;

import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.type.TypeAliasRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 17:56
 */
@Slf4j
public class BaseBuilder {

    protected final  Configuration configuration;

    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        log.info("start building config via resources");
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }
}
