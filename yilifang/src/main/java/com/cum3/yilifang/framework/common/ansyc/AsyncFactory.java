package com.cum3.yilifang.framework.common.ansyc;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cum3.yilifang.framework.common.utils.ServletUtils;
import com.cum3.yilifang.framework.common.utils.SpringUtils;
import com.cum3.yilifang.project.system.operlog.domain.OperLog;
import com.cum3.yilifang.project.system.operlog.service.OperLogService;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 
 * 异步工厂（产生任务用）
 * @author Fandy Liu
 * @created 2018年9月1日 下午8:58:43
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 操作日志记录
     * 
     * @param operLog
     *            操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
               // operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
               try {
                   SpringUtils.getBean(OperLogService.class).save(operLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
               
            }
        };
    }

    
}
