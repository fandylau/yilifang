package com.cum3.yilifang.project.system.job.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.cum3.yilifang.framework.web.mapper.BaseMapper;
import com.cum3.yilifang.project.system.job.domain.JobLog;


@Mapper
public interface JobLogMapper extends BaseMapper<JobLog> {}