package com.cum3.yilifang.framework.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定使用哪个数据源的注解
 * @author Fandy Liu
 * @created 2018年9月26日 下午4:24:54
 */
@Target({ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
    String name();
}
