package com.kandoka.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description Handler for type like String, Long...
 * @Author kandoka
 * @Date 2023/2/14 17:25
 */
public interface TypeHandler<T> {

    /**
     * 设置参数
     */
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;
    /**
     * 获取结果
     */
    T getResult(ResultSet rs, String columnName) throws SQLException;

}
