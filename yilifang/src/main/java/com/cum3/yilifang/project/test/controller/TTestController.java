package com.cum3.yilifang.project.test.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cum3.yilifang.framework.web.controller.AjaxResult;
import com.cum3.yilifang.framework.web.controller.BaseController;
import com.cum3.yilifang.project.system.operlog.Log;
import com.cum3.yilifang.project.system.operlog.enums.BusinessType;
import com.cum3.yilifang.project.system.operlog.enums.OperatorType;
import com.cum3.yilifang.project.test.domain.TTest;
import com.cum3.yilifang.project.test.service.TTestService;


/**
 * 测试
 * 
 * @author Fandy Lau
 */
@Controller
@RequestMapping("/test")
public class TTestController extends BaseController {

    @Autowired
    private TTestService tTestService;

    @GetMapping("/list")
    @ResponseBody
    @Log(title = "操作日志测试", businessType = BusinessType.EXPORT,operatorType = OperatorType.MANAGE,isSaveRequestData= true)
    public AjaxResult list() {
        TTest test = new TTest();
        return success(tTestService.queryPageListByWhere(1, 10, test));
    }
    @GetMapping("/save")
    @ResponseBody
    public AjaxResult save(){
        TTest test = new TTest();
        test.setFiled1("1111");
        test.setFiled2("2222");
        return success(tTestService.save(test));
    }

    @ResponseBody
    public AjaxResult remove(String ids) throws Exception {
        return success(tTestService.deleteByIds(TTest.class, "id",Arrays.asList(ids.split(","))));
    }
    
    @ResponseBody
    @GetMapping("/sqlFilter")
    public AjaxResult testSqlFilter( HttpServletRequest request) {
        return success(tTestService.queryListBySqlTemplate("selectAll",1));
    }


   
}
