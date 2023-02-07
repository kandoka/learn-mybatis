package com.kandoka.mybatis.binding;

import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.mapping.SqlCommandType;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @Description An adaptor between sql command and sql session
 * @Author kandoka
 * @Date 2023/2/6 17:54
 */
@Slf4j
public class MapperMethod {

    private final SqlCommand command;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        log.info("create a mapper method, mapper interface: {}, method: {}", mapperInterface.getCanonicalName(), method.getName());
        this.command = new SqlCommand(configuration, mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result = null;
        log.info("invoke sql session executing by sql command type: {}", command.getType());
        switch (command.getType()) {
            case INSERT:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case SELECT:
                result = sqlSession.selectOne(command.getName(), args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return result;
    }

    /**
     * SQL 指令
     */
    public static class SqlCommand {

        //class name + method name
        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            //find the registered statement info by class name + method name of the mapper
            String statementName = mapperInterface.getName() + "." + method.getName();
            log.info("create a sql command by statement info: {}", statementName);
            MappedStatement ms = configuration.getMappedStatement(statementName);
            name = ms.getId();
            type = ms.getSqlCommandType();
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }
    }

}
