package com.cum3.yilifang.project.test.mapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import com.cum3.yilifang.framework.web.mapper.BaseMapper;
import com.cum3.yilifang.project.test.domain.TTest;


@Mapper
@CacheNamespace
public interface TTestMapper extends BaseMapper<TTest> {}