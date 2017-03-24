package com.sso_server;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.TokenUtil;

@Controller
@RequestMapping("/user")
public class LoginController {

  /**
   * 严重登录是否成功。
   * @param username
   * @param password
   * @param req
   * @return
   */
  @RequestMapping("/login1")
  public String login1(String username, String password, HttpServletRequest req, HttpServletResponse res) {
    final String path = req.getContextPath();
    final String url = req.getScheme() + "://" + req.getServerName() + ":"
        + req.getServerPort() + path + "/";
    String resToken = TokenUtil.postForm(username, password, url);
    
    if (resToken != null && resToken !="") {
      Boolean tokenFlag = TokenUtil.verifyTokenURL(resToken, url);
      
      if(tokenFlag != null && tokenFlag){
        req.getSession().setAttribute("token", resToken);
        System.out.println("token和认证中心匹配成功！");
        
        // 设置cookie
        Cookie userCookie=new Cookie("userToken",resToken); 
        //userCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
        userCookie.setPath("/");
        res.addCookie(userCookie);
        
        return "success";
      }
      return "failure";
    } else {
      req.getSession().setAttribute("token", "");
      return "failure";
    }

  }
  
  /**
   * 跳转到登录页面
   * @return
   */
  @RequestMapping("/login")
  public String login() {
    return "login";

  }
  
  /**
   * 退出登录
   * @return
   */
  @RequestMapping("/logout")
  public String logout(HttpServletRequest req, HttpServletResponse res) {
    String reqToken = null;
    Cookie[] cookies = req.getCookies();
    if(null != cookies){
      for(Cookie cookie : cookies){
        if(cookie.getName().equals("userToken")){
          reqToken = cookie.getValue();
        }
     }
    }
    
    if(null != reqToken){
      Boolean isDel = TokenUtil.delToken(reqToken);
      if (null != isDel && isDel){
        
        if(null != cookies){
          for(Cookie cookie : cookies){
            if(cookie.getName().equals("userToken")){
              cookie.setMaxAge(0);// 如果0，就说明立即删除
              cookie.setPath("/");// 不要漏掉
              res.addCookie(cookie);
            }
         }
        }
        System.out.println("退出成功！");
        return "login";
      }
    }else{
      System.out.println("退出失败!");
      return "failure";
    }
    return "failure";
  }

}
