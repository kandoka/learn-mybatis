package com.kandoka.mybatis.reflection.wrapper;

import com.kandoka.mybatis.reflection.MetaObject;

/**
 * @Description Default define of an object wrapper factory
 * @Author kandoka
 * @Date 2023/2/10 15:34
 */
public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw new RuntimeException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }
}
