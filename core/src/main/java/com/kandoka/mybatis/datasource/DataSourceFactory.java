package com.kandoka.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description Factory of datasource, creating a datasource by inner properties
 * @Author kandoka
 * @Date 2023/2/7 14:47
 */
public interface DataSourceFactory {

    void setProperties(Properties props);

    /**
     * create a datasource by inner properties
     * @return
     */
    DataSource getDataSource();
}
