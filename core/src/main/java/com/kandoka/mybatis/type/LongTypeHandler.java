package com.kandoka.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/15 15:36
 */
public class LongTypeHandler extends BaseTypeHandler<Long> {

    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter);
    }
}
