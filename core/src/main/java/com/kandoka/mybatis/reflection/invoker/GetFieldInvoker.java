package com.kandoka.mybatis.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @Description Invoker of get method
 * @Author kandoka
 * @Date 2023/2/10 15:32
 */
public class GetFieldInvoker implements Invoker {

    private Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        return field.get(target);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
