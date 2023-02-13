package com.kandoka.mybatis.reflection.wrapper;

import com.kandoka.mybatis.reflection.MetaObject;

/**
 * @Description Factory of object wrapper
 * @Author kandoka
 * @Date 2023/2/10 15:34
 */
public interface ObjectWrapperFactory {

    /**
     * 判断有没有包装器
     */
    boolean hasWrapperFor(Object object);

    /**
     * 得到包装器
     */
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}
