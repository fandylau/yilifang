package com.cum3.yilifang.framework.datasource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 动态数据源注册
 * @author Fandy Liu
 * @created 2018年10月2日 上午11:16:41
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
    
    private static final String prefix = "spring.datasource";
    // 数据源配置信息
    private PropertyValues dataSourcePropertyValues;
    // 默认数据源
    private DataSource defaultDataSource;
    // 动态数据源
    private Map<String, DataSource> dynamicDataSources = new HashMap<>();



    /**
     *  加载多数据源配置
     * @param env
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void setEnvironment(Environment env) {
        //获取数据源配置 的节点
        Binder binder = Binder.get(env);
        Map parentMap = binder.bind(prefix, Map.class).get();
        Map common = (Map<String, Object>)((Map<String, Object>) parentMap.get("common")).get("dbconfig");
        //数据源存放的属性
        String dsPrefixs = parentMap.get("names").toString();
        //遍历生成相应的数据源并存储
        for (String dsPrefix : dsPrefixs.split(",")) {
            Map<String,Object> map = (Map<String, Object>) parentMap.get(dsPrefix);
            DataSource ds = null;
            if(map.get("minIdle") == null ){
                ds = initDataSource(map,common );
            }else {
                ds = initDataSource(map,null);
            }
            // 设置默认数据源
            if ("base".equals(dsPrefix)) {
                defaultDataSource = ds;
            } else {
                dynamicDataSources.put(dsPrefix, ds);
            }
            //初始化数据源
            dataBinder(ds, env,dsPrefix);
        }
    }

    /**
     * 初始化数据源
     * @param map
     * @return
     */
    public DataSource initDataSource(Map<String, Object> map ,Map<String, Object> commonMap ) {
        String driverClassName = map.get("driver-class-name").toString();
        String url = map.get("url").toString();
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        Integer minIdle = 0 ;
        Integer maxActive =0;
        Long maxWait;
        Long minEvictableIdleTimeMillis;
        if(map.get("minIdle") == null ){
            minIdle = Integer.parseInt(commonMap.get("minIdle").toString());
            maxActive = Integer.parseInt(commonMap.get("maxActive").toString());
            maxWait = Long.parseLong(commonMap.get("maxWait").toString());
            minEvictableIdleTimeMillis =  Long.parseLong(commonMap.get("minEvictableIdleTimeMillis").toString());
        }else{
            minIdle = Integer.parseInt(map.get("minIdle").toString());
            maxActive = Integer.parseInt(map.get("maxActive").toString());
            maxWait = Long.parseLong(map.get("maxWait").toString());
            minEvictableIdleTimeMillis =  Long.parseLong(map.get("minEvictableIdleTimeMillis").toString());
        }

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setDriverClassName(driverClassName);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setMinIdle(minIdle);
        datasource.setMaxWait(maxWait);
        datasource.setMaxActive(maxActive);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        try {
            //开启Druid的监控统计功能 stat表示sql合并,wall表示防御SQL注入攻击
            datasource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }

    /**
     * 加载数据源配置信息
     * @param dataSource
     * @param env
     */
    private void dataBinder(DataSource dataSource, Environment env,String defix) {
        Binder binder = Binder.get(env);
        binder.bind( defix,Bindable.ofInstance(dataSource));
    }

    /**
     * 注册数据源been
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("dataSource", defaultDataSource);
        // 添加更多数据源
        targetDataSources.putAll(dynamicDataSources);
        // 创建动态数据源DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);

    }

}
