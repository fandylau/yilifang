package com.cum3.yilifang.project.system.operlog.enums;

/**
 * 业务操作类型
 * 
 * @author Fandy Lau
 *
 */
public enum BusinessType
{
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,
    /**
     * 新增或修改
     */
    INSERORUPDATE,
    
    /**
     * 新增或删除
     */
    INSERORDELETE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE
}
