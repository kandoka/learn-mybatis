package com.kandoka.mybatis.reflection.invoker;

import java.lang.reflect.Method;

/**
 * @Description Invoder of a method
 * @Author kandoka
 * @Date 2023/2/10 15:32
 */
public class MethodInvoker implements Invoker {

    private Class<?> type;
    private Method method;

    public MethodInvoker (Method method) {
        this.method = method;
        if (method.getParameterTypes().length == 1) {
            type = method.getParameterTypes()[0];
        } else {
            type = method.getReturnType();
        }
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        return method.invoke(target, args);
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
