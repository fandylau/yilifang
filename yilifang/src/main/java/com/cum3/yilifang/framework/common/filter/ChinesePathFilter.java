package com.cum3.yilifang.framework.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 中文路径过滤
 * @author Fandy Liu
 * @created 2018年8月25日 上午12:27:14
 */
public class ChinesePathFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(request.getScheme()).append("://").append(request.getServerName()).append(":")
                .append(request.getServerPort()).append(((HttpServletRequest)request).getContextPath()).append("/");
        request.setAttribute("baseUrl", pathBuilder.toString());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
