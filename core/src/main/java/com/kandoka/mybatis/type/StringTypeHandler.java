package com.kandoka.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/15 15:36
 */
public class StringTypeHandler extends BaseTypeHandler<String> {

    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }
}
