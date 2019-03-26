package com.cum3.yilifang.framework.web.controller;
/**
 * 
 * 前端约定传递的固定参数
 * @author Fandy Liu
 * @created 2018年9月25日 上午1:15:48
 */
public enum PassParams {
    OPENID("openId", null),
    COMCODE("comCode", null),
    PKVALUE("pkValue", null),
    PKVALUES("pkValues", null),
    PID("pid",null),
    SQLID("sqlId", null),
    PARAMS("params", null),
    PAGE("page",1),
    ROWS("row",20),
    START("start", 1),
    LIMIT("limit", 20),
    CASENTITYS("casEntitys",null),
    CASFKCOLUMNS("casFkColumns",null),
    SORTBY("sortBy", "createTime"),
    SORTTYPE("sortType", "desc"),
    OPICTURES("oPictures",null),
    OVIDEOS("oVideos",null),
    OVOICE("oVoices",null);
    private String name;
    private Object defaultValue;

    private PassParams(String name, Object defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public static PassParams fromPassName(String name) {
        for (PassParams p : PassParams.values()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    

    
}
