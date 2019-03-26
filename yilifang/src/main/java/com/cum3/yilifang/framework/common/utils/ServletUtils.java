package com.cum3.yilifang.framework.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.util.WebUtils;

import com.cum3.yilifang.framework.common.support.Convert;

/**
 * 客户端工具类
 * 
 * @author Fandy Lau
 */
public class ServletUtils {
    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }
    
    

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
    /**
     * 获取reqeust中的bean对象
     * @param beanClass
     * @return
     */
    public static <T> T getRequestBean(Class<T> beanClass) {
        String jsonStr = JSONUtils.toJSONString(getParameterMap(), false);
        return  JSONUtils.getObject(jsonStr, beanClass);
    }

    /**
     * 将字符串渲染到客户端
     * 
     * @param response
     *            渲染对象
     * @param string
     *            待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
   /**
    * 返回值类型为Map<String, Object>
    * @author Fandy Liu
    * @created 2018年8月30日 下午8:18:45
    * @param request
    * @return
    */
    public static Map<String, Object> getParameterMap() {
        Map<String, String[]> properties = getRequest().getParameterMap();//把请求参数封装到Map<String, String[]>中
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Iterator<Entry<String, String[]>> iter = properties.entrySet().iterator();
        String name = "";
        String value = "";
        while (iter.hasNext()) {
            Entry<String, String[]> entry = iter.next();
            name = entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();//用于请求参数中请求参数名唯一
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
    /**
     * 返回值类型为Map<String, String>
     * @param request
     * @return
     */
    public static Map<String, String> getParameterStringMap() {
        Map<String, String[]> properties =  getRequest().getParameterMap();//把请求参数封装到Map<String, String[]>中
        Map<String, String> returnMap = new HashMap<String, String>();
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                value = "";
            } else if (values.length>1) {
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = values[0];//用于请求参数中请求参数名唯一
            }
            returnMap.put(name, value);
            
        }
        return returnMap;
    }
    /**
     * 获取请求参数 字符串
     * @author Fandy Liu
     * @created 2018年9月1日 下午5:31:11
     * @param request
     * @return
     */
    public static String getRequestParameterStr(){
        InputStream inputStream = null;
        BufferedReader in = null ;
        try {
            inputStream =  getRequest().getInputStream();
            StringBuffer sb = new StringBuffer();
            String s;
            in= new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                in.close();
                inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
        }
        return null;
    }

    /**
     * 是否是Ajax异步请求
     * 
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {

        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.inStringIgnoreCase(ajax, "json", "xml")) {
            return true;
        }

        return false;
    }
    /**
     * 描述 是否是多文件请求
     * @author Fandy Liu
     * @created 2018年10月5日 下午2:23:26
     * @return
     */
    public static boolean isMultipartReqeust(){
        String contentType = getRequest().getContentType();
        return  (contentType != null && contentType.toLowerCase().startsWith("multipart/")) ;
    }
    /**
     * 获取上传的文件
     * @return
     */
    public static MultipartFile getMultipartFile(){
        HttpServletRequest request = getRequest();
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
       // MultipartHttpServletRequest multipartRequest =WebUtils.getNativeRequest(getRequest(), MultipartHttpServletRequest.class);
        return multipartRequest.getFile("file");  
    }
}
