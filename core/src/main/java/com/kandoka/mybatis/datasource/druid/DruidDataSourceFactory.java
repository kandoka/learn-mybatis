package com.kandoka.mybatis.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.kandoka.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/7 14:47
 */
public class DruidDataSourceFactory implements DataSourceFactory {

    private Properties props;

    @Override
    public void setProperties(Properties props) {
        this.props = props;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(props.getProperty("driver"));
        dataSource.setUrl(props.getProperty("url"));
        dataSource.setUsername(props.getProperty("username"));
        dataSource.setPassword(props.getProperty("password"));
        return dataSource;
    }
}
