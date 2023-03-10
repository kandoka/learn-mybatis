package com.kandoka.mybatis.binding;

import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.mapping.SqlCommandType;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description An adaptor between sql command and sql session
 * @Author kandoka
 * @Date 2023/2/6 17:54
 */
public class MapperMethod {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.EXECUTE, MapperMethod.class);

    private final SqlCommand command;
    private final MethodSignature method;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        log.info("create a mapper method, mapper interface: {}, method: {}", mapperInterface.getCanonicalName(), method.getName());
        this.command = new SqlCommand(configuration, mapperInterface, method);
        this.method = new MethodSignature(configuration, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result = null;
        log.info("invoke sql session executing by sql command type: {}", command.getType());
        switch (command.getType()) {
            case INSERT: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = sqlSession.insert(command.getName(), param);
                break;
            }
            case DELETE: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = sqlSession.delete(command.getName(), param);
                break;
            }
            case UPDATE: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = sqlSession.update(command.getName(), param);
                break;
            }
            case SELECT: {
                Object param = method.convertArgsToSqlCommandParam(args);
                if (method.returnsMany) {
                    result = sqlSession.selectList(command.getName(), param);
                } else {
                    result = sqlSession.selectOne(command.getName(), param);
                }
                break;
            }
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return result;
    }

    /**
     * SQL ??????
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

    /**
     * ????????????
     */
    public static class MethodSignature {

        private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.PARAMETER, MethodSignature.class);

        private final boolean returnsMany;
        private final Class<?> returnType;
        private final SortedMap<Integer, String> params;

        public MethodSignature(Configuration configuration, Method method) {
            this.returnType = method.getReturnType();
            this.returnsMany = (configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray());
            this.params = Collections.unmodifiableSortedMap(getParams(method));
        }

        public Object convertArgsToSqlCommandParam(Object[] args) {
            log.info("Convert arguments to sql parameters: {}", args);
            final int paramCount = params.size();
            if (args == null || paramCount == 0) {
                //???????????????
                return null;
            } else if (paramCount == 1) {
                return args[params.keySet().iterator().next().intValue()];
            } else {
                // ?????????????????????ParamMap?????????????????????????????????????????????
                final Map<String, Object> param = new ParamMap<Object>();
                int i = 0;
                for (Map.Entry<Integer, String> entry : params.entrySet()) {
                    // 1.????????????#{0},#{1},#{2}...??????
                    param.put(entry.getValue(), args[entry.getKey().intValue()]);
                    // issue #71, add param names as param1, param2...but ensure backward compatibility
                    final String genericParamName = "param" + (i + 1);
                    if (!param.containsKey(genericParamName)) {
                        /*
                         * 2.????????????#{param1},#{param2}...??????
                         * ???????????????????????????????????????????????????????????????????????????,
                         * ????????????????????????????????????????????????????????????????????????,??????:#{param1},#{param2}??????
                         * ?????????????????????????????????(????????????????????????) ,?????????????????????????????????@Param(???paramName???)?????????
                         */
                        param.put(genericParamName, args[entry.getKey()]);
                    }
                    i++;
                }
                return param;
            }
        }

        private SortedMap<Integer, String> getParams(Method method) {
            // ?????????TreeMap????????????????????????????????????????????????
            final SortedMap<Integer, String> params = new TreeMap<Integer, String>();
            final Class<?>[] argTypes = method.getParameterTypes();
            for (int i = 0; i < argTypes.length; i++) {
                String paramName = String.valueOf(params.size());
                // ?????? Param ?????????????????????????????????????????????????????????????????? Param ???????????????????????????
                params.put(i, paramName);
            }
            return params;
        }

    }

    /**
     * ??????map??????????????????,????????????get??????????????????????????????key?????????
     */
    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -2212268410512043556L;

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new RuntimeException("Parameter '" + key + "' not found. Available parameters are " + keySet());
            }
            return super.get(key);
        }

    }
}
