package com.open.capacity.datasource.util;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.open.capacity.datasource.constant.DataSourceKey;

/**
 * spring动态数据源（需要继承AbstractRoutingDataSource）
 *
 * @author owen
 * @create 2017年7月2日
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> datasources;

    public DynamicDataSource() {
        datasources = new HashMap<>();

        super.setTargetDataSources(datasources);

    }

    public <T extends DataSource> void addDataSource(DataSourceKey key, T data) {
        datasources.put(key, data);
    }

    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSourceKey();
    }

}