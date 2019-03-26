package com.cum3.yilifang.framework.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 系统首页的controller  写上首页的跳转业务
 * 
 * @author Fandy Lau
 */
@Controller
public class IndexController extends BaseController {



    @GetMapping("/index")
    public String list() {
        
        return "index";
    }
    
    @GetMapping("/upload")
    public String attach() {
       
        return "upload3";
    }
    
    @GetMapping("/mine")
    public String mine() {
        return "mine";
    }
  


   
}
