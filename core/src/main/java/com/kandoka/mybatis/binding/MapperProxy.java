package com.kandoka.mybatis.binding;

import cn.hutool.core.util.StrUtil;
import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description A proxy for mapper interface, wrapping a sql session with it
 * @Author kandoka
 * @Date 2023/2/6 11:06
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.EXECUTE, MapperProxy.class);

    private static final long serialVersionUID = -5535484319931298612L;

    private SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            log.info("invoke the mapper method directly, {}.{}", proxy.getClass().getCanonicalName(), method.getName());
            return method.invoke(this, args);
        } else {
            log.info("invoke the mapper method by proxy, {}.{}", proxy.getClass().getCanonicalName(), method.getName());
            final MapperMethod mapperMethod = cachedMapperMethod(method);
            return mapperMethod.execute(sqlSession, args);
        }
    }

    /**
     * 去缓存中找MapperMethod
     */
    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) {
            //找不到才去new
            mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }
}
