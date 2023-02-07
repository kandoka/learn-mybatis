package com.kandoka.mybatis.binding;

import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.util.StrUtil;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description 提供包路径的扫描和映射器代理类注册机服务，完成接口对象的代理类注册处理。
 * @Author kandoka
 * @Date 2023/2/6 16:37
 */
public class MapperRegistry {

    private Configuration configuration;

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * added mappers, as a registry
     */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if(mapperProxyFactory == null) {
            throw new RuntimeException(StrUtil.format("Type {} is not known to the registry", type));
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException(StrUtil.format("Error getting mapper instance, cause: {}", e), e);
        }
    }

    private <T> boolean hasMapper(Class<T> type) {
        for(Class<?> existed: knownMappers.keySet()) {
            if(StrUtil.equals(type.getCanonicalName(), existed.getCanonicalName())) {
                return true;
            }
        }
        return false;
    }

    public <T> void addMapper(Class<T> type) {
        /* Mapper 必须是接口才会注册 */
        if (type.isInterface()) {
            if (hasMapper(type)) {
                // 如果重复添加了，报错
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            // 注册映射器代理工厂
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    public void addMappers(String packageName) {
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for (Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }
}
