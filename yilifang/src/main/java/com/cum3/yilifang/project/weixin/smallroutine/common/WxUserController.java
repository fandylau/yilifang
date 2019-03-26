package com.cum3.yilifang.project.weixin.smallroutine.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cum3.yilifang.framework.common.utils.HttpUtils;
import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.common.utils.StringUtils;
import com.cum3.yilifang.framework.web.controller.AjaxResult;
import com.cum3.yilifang.framework.web.controller.BaseController;


/**
 * 小程序初始访问的接口
 * @author Fandy Lau
 */
@RestController
@RequestMapping("/user")
public class WxUserController extends BaseController {
    @Value("${smallrouting.appId}")
    private String appid ;
    @Value("${smallrouting.secret}")
    private String secret;
    
    private static final String URL_GET_OPENID = "https://api.weixin.qq.com/sns/jscode2session"; 
    
    //private static final String RUL_GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String RUL_GET_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
   
    private static final String URL_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
    
    @GetMapping("/getOpenId")
    @ResponseBody
    public AjaxResult getOpenId(@RequestBody Map<String,Object> map) {
       String code = (String)((Map<String,Object>)map.get("passdata")).get("code");
       String param = StringUtils.format("appid={}&secret={}&js_code={}&grant_type=authorization_code",appid,secret,code);
       String ret = HttpUtils.sendGet( "https://api.weixin.qq.com/sns/jscode2session", param);
       String param2 = "appid={}&secret={}&code={}&grant_type=authorization_code";
       String ret2 = HttpUtils.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token", StringUtils.format(param2, appid,secret,code));
       String accessToken = JSONUtils.json2Map(ret2).get("access_token").toString();
       String opendId = JSONUtils.json2Map(ret).get("openid").toString();
       String params3 = StringUtils.format("access_token={}&openid={}&lang=zh_CN",accessToken,opendId);
       String ret3 = HttpUtils.sendGet("https://api.weixin.qq.com/sns/userinfo", params3);
       System.out.println(ret);
       System.out.println(ret2);
       System.out.println(ret3);
       
       return success(ret2);
    }
    

   
}
