package com.kandoka.mybatis.binding;

import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description Factory of  MapperProxy
 * @Author kandoka
 * @Date 2023/2/6 11:06
 */
public class MapperProxyFactory<T> {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.EXECUTE, MapperProxyFactory.class);
    /**
     * Mapper.class
     */
    private final Class<T> mapperInterface;

    private Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<Method, MapperMethod>();


    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession) {
        log.info("create a mapper proxy for: {}", mapperInterface.getCanonicalName());
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
