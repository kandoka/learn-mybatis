package com.kandoka.mybatis.reflection;

import com.kandoka.mybatis.reflection.factory.DefaultObjectFactory;
import com.kandoka.mybatis.reflection.factory.ObjectFactory;
import com.kandoka.mybatis.reflection.wrapper.DefaultObjectWrapperFactory;
import com.kandoka.mybatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/10 15:32
 */
public class SystemMetaObject {

    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

    private SystemMetaObject() {
        // Prevent Instantiation of Static Class
    }

    /**
     * 空对象
     */
    private static class NullObject {
    }

    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
    }

}
