package com.cum3.yilifang.framework.common.genid;

import java.util.UUID;

import tk.mybatis.mapper.genid.GenId;
/**
 * 描述 全局主键 默认UUID生成
 * @author Fandy Liu
 * @created 2018年9月2日 上午1:50:04
 */
public class UUIdGenId implements GenId<String> {
    @Override
    public String genId(String table, String column) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}