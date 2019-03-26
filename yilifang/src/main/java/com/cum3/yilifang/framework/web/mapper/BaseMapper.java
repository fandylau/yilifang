package com.cum3.yilifang.framework.web.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用增删改查
 * @author Fandy Liu
 * @created 2018年8月30日 上午8:54:54
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>{}