package com.cum3.yilifang.tool.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cum3.yilifang.framework.web.controller.BaseController;

/**
 * swagger 接口
 * 
 * @author Fandy Lau
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController extends BaseController
{

    @GetMapping()
    public String index()
    {
        return redirect("/swagger-ui.html");
    }
}
