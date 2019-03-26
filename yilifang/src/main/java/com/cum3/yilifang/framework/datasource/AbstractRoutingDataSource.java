package com.cum3.yilifang.framework.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public abstract class AbstractRoutingDataSource<T> extends AbstractDataSource implements InitializingBean {
    /**
     * 存放所有的数据源的map 根据key 值可获取对应的数据源
     */
    @SuppressWarnings("rawtypes")
    private @Nullable Map targetDataSources;
    /**
     * 默认的数据源
     */
    private @Nullable Object defaultTargetDataSource;

    private boolean lenientFallback = true;

    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

    @SuppressWarnings("rawtypes")
    private @Nullable Map resolvedDataSources;

    private @Nullable DataSource resolvedDefaultDataSource;

    public AbstractRoutingDataSource() {
    }

    @SuppressWarnings("rawtypes")
    public void setTargetDataSources(Map targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    public void setLenientFallback(boolean lenientFallback) {
        this.lenientFallback = lenientFallback;
    }

    public void setDataSourceLookup(@Nullable DataSourceLookup dataSourceLookup) {
        this.dataSourceLookup = (DataSourceLookup) (dataSourceLookup != null ? dataSourceLookup
                : new JndiDataSourceLookup());
    }

    /**
     * 初始化bean的时候执行，可以针对某个具体的bean进行配置。afterPropertiesSet 必须实现
     * InitializingBean接口。实现
     * InitializingBean接口必须实现afterPropertiesSet方法。方法体是将数据源分别进行复制到resolvedDataSources和resolvedDefaultDataSource中
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void afterPropertiesSet() {
        if (this.targetDataSources == null) {
            throw new IllegalArgumentException("Property 'targetDataSources' is required");
        } else {
            this.resolvedDataSources = new HashMap(this.targetDataSources.size());
            this.targetDataSources.forEach((key, value) -> {
                Object lookupKey = this.resolveSpecifiedLookupKey(key);
                DataSource dataSource = this.resolveSpecifiedDataSource(value);
                this.resolvedDataSources.put(lookupKey, dataSource);
            });
            if (this.defaultTargetDataSource != null) {
                this.resolvedDefaultDataSource = this.resolveSpecifiedDataSource(this.defaultTargetDataSource);
            }
        }
    }

    protected Object resolveSpecifiedLookupKey(Object lookupKey) {
        return lookupKey;
    }
    protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        if (dataSource instanceof DataSource) {
            return (DataSource) dataSource;
        } else if (dataSource instanceof String) {
            return this.dataSourceLookup.getDataSource((String) dataSource);
        } else {
            throw new IllegalArgumentException("Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
        }
    }
    /**
     * 先调用determineTargetDataSource()方法返回DataSource在进行getConnection()
     * @return
     * @throws SQLException
     * @see javax.sql.DataSource#getConnection()
     */
    public Connection getConnection() throws SQLException {
        return this.determineTargetDataSource().getConnection();
    }
    
    public Connection getConnection(String username, String password) throws SQLException {
        return this.determineTargetDataSource().getConnection(username, password);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public T unwrap(Class iface) throws SQLException {
        return iface.isInstance(this)?(T)this :(T)this.determineTargetDataSource().unwrap(iface);
    }
    @SuppressWarnings("rawtypes")
    public boolean isWrapperFor(Class iface) throws SQLException {
        return iface.isInstance(this)||this.determineTargetDataSource().isWrapperFor(iface);
    }
    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
        // 这里就是根据determineCurrentLookupKey获取相应的key下面再根据key值进行获取相应的数据源.
        Object lookupKey = this.determineCurrentLookupKey();
        DataSource dataSource = (DataSource) this.resolvedDataSources.get(lookupKey);
        if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
            dataSource = this.resolvedDefaultDataSource;
        }
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        } else {
            return dataSource;
        }
    }
    //重写此方法就可以进行
    protected abstract @Nullable Object determineCurrentLookupKey();
}
