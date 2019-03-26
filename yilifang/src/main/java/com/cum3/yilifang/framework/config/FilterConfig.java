package com.cum3.yilifang.framework.config;

import java.util.Map;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cum3.yilifang.framework.common.filter.ChinesePathFilter;
import com.cum3.yilifang.framework.common.filter.HttpServletRequestWrapperFilter;
import com.cum3.yilifang.framework.common.filter.XssFilter;
import com.cum3.yilifang.framework.common.utils.StringUtils;
import com.google.common.collect.Maps;

/**
 * Filter配置
 *
 * @author Fandy Lau
 */
@Configuration
public class FilterConfig {
    @Value("${xss.enabled}")
    private String enabled;

    @Value("${xss.excludes}")
    private String excludes;

    @Value("${xss.urlPatterns}")
    private String urlPatterns;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put("excludes", excludes);
        initParameters.put("enabled", enabled);
        registration.setInitParameters(initParameters);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<ChinesePathFilter> chinesePathFilter() {
        ChinesePathFilter filter = new ChinesePathFilter();
        FilterRegistrationBean<ChinesePathFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setName("chinesePathFilter");
        registration.addUrlPatterns("/*");
        return registration;
    }

   @Bean
    public FilterRegistrationBean<HttpServletRequestWrapperFilter> httpServletRequestWrapperFilter() {
        HttpServletRequestWrapperFilter filter = new HttpServletRequestWrapperFilter();
        FilterRegistrationBean<HttpServletRequestWrapperFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setName("httpServletRequestWrapperFilter");
        registration.addUrlPatterns("/*");
        return registration;
    }
}
