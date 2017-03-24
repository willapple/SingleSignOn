package com.sso_client;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.TokenUtil;

public class LoginFilter implements Filter {

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String url = req.getServletPath();
    String resource = req.getRequestURI();
    if (resource.contains(".css") || resource.contains(".js")
        || resource.contains(".png") || resource.contains(".jpg")) {
      // 如果发现是css、js文件或者图片，直接放行
      chain.doFilter(request, response);
      return;
    }

    if(url.endsWith("/user/login") || url.endsWith("/user/login1")){
      chain.doFilter(request, response);
      return;
    }

    Cookie[] cookies = req.getCookies();
    if(null != cookies){
      for(Cookie cookie : cookies){
        if(cookie.getName().equals("userToken")){
            String token = cookie.getValue();
           
            System.out.println("token=" + token);
            
             String path = req.getContextPath();
             String url1 = req.getScheme() + "://" + req.getServerName() + ":"
                + req.getServerPort() + path + "/";
            
             Boolean tokenFlag = TokenUtil.verifyTokenURL(token, url1);
             if(tokenFlag != null && tokenFlag){
               chain.doFilter(request, response);
               return;
             }
            
        }
     }
    }
    
    // 跳转至sso认证中心
    res.sendRedirect("user/login");
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    // TODO Auto-generated method stub

  }
}
