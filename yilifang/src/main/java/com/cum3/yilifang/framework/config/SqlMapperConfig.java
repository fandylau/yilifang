package com.cum3.yilifang.framework.config;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cum3.yilifang.framework.web.mapper.SqlMapper;

/**
 * 描述
 * 
 * @author Fandy Liu
 * @created 2018年8月30日 下午3:20:24
 */
@Configuration
public class SqlMapperConfig {
    @Autowired
    private SqlSession sqlSesstion;

    /**
     * 创建sqlMapper 用于执行sql语句
     */
    @Bean(name="sqlMapper")
    public SqlMapper createSqlMapper() {
       return new SqlMapper(sqlSesstion);
    }
}
