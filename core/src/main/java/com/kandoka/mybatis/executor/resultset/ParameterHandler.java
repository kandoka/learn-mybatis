package com.kandoka.mybatis.executor.resultset;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description A parameter handler sets the parameters of the PreparedStatement.
 * @Author kandoka
 * @Date 2023/2/15 15:35
 */
public interface ParameterHandler {
    /**
     * 获取参数
     */
    Object getParameterObject();

    /**
     * 设置参数
     */
    void setParameters(PreparedStatement ps) throws SQLException;

}
