package com.kandoka.mybatis.reflection.invoker;

/**
 * @Description Define an invoker
 * @Author kandoka
 * @Date 2023/2/10 15:32
 */
public interface Invoker {

    Object invoke(Object target, Object[] args) throws Exception;

    Class<?> getType();
}
