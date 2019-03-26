package com.cum3.yilifang.framework.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @author Fandy Liu
 * @created 2018年8月22日 下午11:10:25
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();
    /**
     * 设置当前线程 数据源key
     * @param dataSourceType
     */
    public static void setDataSourceKey(String dataSourceType){
        dataSourceKey.set(dataSourceType);
    }
    /**
     * 
     * 获取当前线程的数据源key
     * @return
     */
    public static String getDataSourceKey(){
        return dataSourceKey.get();
    }
    /**
     * 清除数据源
     */
    public static void clearDataSourceKey(){
        
    }
    /**
     * 该方法返回值就是项目中所用到的datasource的key值,
     * 拿到key后就可在resolveDataSource中取出相应的datasource，
     * 如果key找不到就使用默认的dataSource数据源
     * @return
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return  getDataSourceKey();
    }

}