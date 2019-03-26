package com.cum3.yilifang.framework.web.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 前端传递的附件信息
 * @author Fandy Liu
 * @created 2018年9月26日 上午12:18:32
 */
@Data
@RequiredArgsConstructor
public class Media {
   
    /**
     * 附件url
     */
    @NonNull
    private String url;
    /**
     * 附件状态 0 无效  1有效
     */
    @NonNull
    private Integer status;
    
}
