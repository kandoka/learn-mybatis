package com.kandoka.mybatis.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @Description Invoker of set method
 * @Author kandoka
 * @Date 2023/2/10 15:33
 */
public class SetFieldInvoker implements Invoker{

    private Field field;

    public SetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        field.set(target, args[0]);
        return null;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
