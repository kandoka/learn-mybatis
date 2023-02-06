package com.kandoka.mybatis.binding;

import cn.hutool.core.util.StrUtil;
import com.kandoka.mybatis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description 负责实现 InvocationHandler 接口的 invoke 方法，最终所有的实际调用都会调用到这个方法包装的逻辑。
 * @Author kandoka
 * @Date 2023/2/6 11:06
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -5535484319931298612L;

    private SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            return StrUtil.format("you have been proxied! {}",
                    sqlSession.selectOne(
                             StrUtil.format("{}.{}", mapperInterface.getName(), method.getName()),
                            args
                    ).toString()
            );
        }
    }
}
