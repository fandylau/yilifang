package com.cum3.yilifang.framework.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.cum3.yilifang.framework.common.utils.StringUtils;
import com.cum3.yilifang.framework.common.utils.date.DateStyle;
import com.cum3.yilifang.framework.common.utils.sql.SqlFilter;

/**
 * web层通用数据处理
 * 
 * @author Fandy Lau
 */
public class BaseController {
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateStyle.MM_DD_HH_MM_SS.getValue());
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    
    /**
     * 初始化sqlFilter
     * @author Fandy Liu
     * @param request
     * @param sortBy
     * @param sortType
     * @return
     */
    protected SqlFilter initSqlFilter(String  jsonStr,String sortBy,String sortType ){
        SqlFilter sqlFilter = new SqlFilter(jsonStr);
        if(StringUtils.isNotEmpty(sortBy)){
            sqlFilter.addSort(sortBy);
        }
        if(StringUtils.isNotEmpty(sortType)){
            sqlFilter.addOrder(sortType);
        }
        return sqlFilter;
    }
    
   
    
    /**
     * 响应返回结果
     * 
     * @param rows
     *            影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 返回成功
     */
    public AjaxResult success() {
        return AjaxResult.success();
    }
    /**
     * 返回成功
     */
    public AjaxResult success(int code,Object result) {
         AjaxResult ret = new AjaxResult();
         ret.put("code", result);
         return ret;
         
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }
    
    /**
     * 返回成功消息
     */
    public AjaxResult success(Object value) {
        return AjaxResult.success(value);
    }


    /**
     * 返回失败消息
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(int code, String message) {
        return AjaxResult.error(code, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

}
