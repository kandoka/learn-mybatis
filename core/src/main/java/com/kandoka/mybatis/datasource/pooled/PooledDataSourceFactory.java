package com.kandoka.mybatis.datasource.pooled;

import com.kandoka.mybatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;

/**
 * @Description Factory of data source with pool
 * @Author kandoka
 * @Date 2023/2/8 10:27
 */
public class PooledDataSourceFactory extends UnpooledDataSourceFactory {

    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }
}
