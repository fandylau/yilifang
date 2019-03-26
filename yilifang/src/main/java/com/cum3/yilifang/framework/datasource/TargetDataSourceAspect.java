package com.cum3.yilifang.framework.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 利用aop原理实现切换数据源
 * 
 * @author Fandy Liu
 * @created 2018年9月26日 下午4:26:00
 */
@Aspect
@Component
@Order(1)
public class TargetDataSourceAspect {

    /**
     * 
     * 根据@TargetDataSource的name值设置不同的DataSource
     * 
     * @param joinPoint
     * 
     * @param targetDataSource
     * 
     */

    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        DynamicDataSource.setDataSourceKey(targetDataSource.name());

    }

    /**
     * 
     * 方法执行完之后清楚当前数据源，让其使用默认数据源
     * 
     * @param joinPoint
     * 
     * @param targetDataSource
     * 
     */

    @After("@annotation(targetDataSource)")
    public void restoreDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        DynamicDataSource.clearDataSourceKey();

    }

}
