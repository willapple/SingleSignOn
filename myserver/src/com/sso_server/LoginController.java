package com.sso_server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.RedisCache;

@Controller
@RequestMapping("/user")
public class LoginController {

   @Autowired
   RedisCache redisCache;

  public String token=null;

  @RequestMapping("/loginTest")
  @ResponseBody
  public String loginTest(String username, String password, String url, HttpServletRequest req) {
    boolean loginFlag = false;
    token = Tool.getToken() + username;
    loginFlag = this.checkLogin(username, password);
    
    if(loginFlag){
      return token;
    }else{
      return "failure";
    }

  }
  
  @RequestMapping("/verifyTokenURL")
  @ResponseBody
  public String verifyTokenURL(String reqToken, String url, HttpServletRequest req) {
    if(token.equals(reqToken)){
      redisCache.addMember(token, url, 600);
      return "1";
    }else{
      return "0";
    }
    
  }
  
  @RequestMapping("/verifyToken")
  @ResponseBody
  public String verifyToken(String reqToken, HttpServletRequest req) {
    if(token.equals(reqToken)){
      return "success123";
    }else{
      return "失败，token不匹配";
    }
    
  }
  
  /**
   * 检查是否登录
   */
  public boolean checkLogin(String username, String password) {
    if ("test".equals(username) && "123".equals(password)) {
      System.out.println("登录成功！");
      System.out.println("token=" + token);
      return true;
    } else {
      return false;
    }
  }
  
  
  /**
   * 退出登录
   * @return
   */
  @RequestMapping("/logout")
  @ResponseBody
  public String logout(String reqToken) {
    if(token.equals(reqToken)){
      if(null != reqToken){
        Long val = redisCache.del(token);
        if(1 == val){
          return "1";
        }else {
          return "0";
        }
      } else {
        return "0";
      }
    } else {
      return "0";
    }
    
  }
}
