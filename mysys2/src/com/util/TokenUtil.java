package com.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TokenUtil {

  /**
   * post方式提交表单（模拟用户登录请求）
   */
  public static String postForm(String username, String password, String url) {

    String isLogin = null;

    // 创建默认的httpClient实例.
    CloseableHttpClient httpclient = HttpClients.createDefault();
    // 创建httppost
    HttpPost httppost = new HttpPost(
        "http://localhost:8888/myserver/user/loginTest");
    // 创建参数队列
    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    formparams.add(new BasicNameValuePair("username", username));
    formparams.add(new BasicNameValuePair("password", password));
    formparams.add(new BasicNameValuePair("url", url));
    UrlEncodedFormEntity uefEntity;
    try {
      uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
      httppost.setEntity(uefEntity);
      System.out.println("executing request " + httppost.getURI());
      CloseableHttpResponse response = httpclient.execute(httppost);
      try {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          isLogin = EntityUtils.toString(entity, "UTF-8");
        }
      } finally {
        response.close();
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭连接,释放资源
      try {
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isLogin;
  }

  /**
   * 通过token和url验证是否登录成功，是否需要重新登录。
   */
  public static Boolean verifyTokenURL(String reqToken, String url) {

    Boolean isLogin = false;

    // 创建默认的httpClient实例.
    CloseableHttpClient httpclient = HttpClients.createDefault();
    // 创建httppost
    HttpPost httppost = new HttpPost(
        "http://localhost:8888/myserver/user/verifyTokenURL");
    // 创建参数队列
    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    formparams.add(new BasicNameValuePair("reqToken", reqToken));
    formparams.add(new BasicNameValuePair("url", url));
    UrlEncodedFormEntity uefEntity;
    try {
      uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
      httppost.setEntity(uefEntity);
      System.out.println("executing request " + httppost.getURI());
      CloseableHttpResponse response = httpclient.execute(httppost);
      try {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          String str = EntityUtils.toString(entity, "UTF-8");
          if("1".equals(str)){
            isLogin = true;
            return isLogin;
          }
        }
      } finally {
        response.close();
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭连接,释放资源
      try {
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isLogin;
  }
  
  /**
   * 验证token是否匹配。
   */
  public static String verifyToken(String reqToken) {

    String isLogin = null;

    // 创建默认的httpClient实例.
    CloseableHttpClient httpclient = HttpClients.createDefault();
    // 创建httppost
    HttpPost httppost = new HttpPost(
        "http://localhost:8888/myserver/user/verifyToken");
    // 创建参数队列
    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    formparams.add(new BasicNameValuePair("reqToken", reqToken));
    UrlEncodedFormEntity uefEntity;
    try {
      uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
      httppost.setEntity(uefEntity);
      System.out.println("executing request " + httppost.getURI());
      CloseableHttpResponse response = httpclient.execute(httppost);
      try {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          isLogin = EntityUtils.toString(entity, "UTF-8");
        }
      } finally {
        response.close();
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭连接,释放资源
      try {
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isLogin;
  }
  
  /**
   * 验证是否包含该系统URL地址。
   */
  public static String verifyURL(String url) {

    String isLogin = null;

    // 创建默认的httpClient实例.
    CloseableHttpClient httpclient = HttpClients.createDefault();
    // 创建httppost
    HttpPost httppost = new HttpPost(
        "http://localhost:8888/myserver/user/verifyURL");
    // 创建参数队列
    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    formparams.add(new BasicNameValuePair("url", url));
    UrlEncodedFormEntity uefEntity;
    try {
      uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
      httppost.setEntity(uefEntity);
      System.out.println("executing request " + httppost.getURI());
      CloseableHttpResponse response = httpclient.execute(httppost);
      try {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          isLogin = EntityUtils.toString(entity, "UTF-8");
        }
      } finally {
        response.close();
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭连接,释放资源
      try {
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isLogin;
  }

  /**
   * 退出登录，删除token。
   */
  public static Boolean delToken(String reqToken) {

    Boolean isDel = false;

    // 创建默认的httpClient实例.
    CloseableHttpClient httpclient = HttpClients.createDefault();
    // 创建httppost
    HttpPost httppost = new HttpPost(
        "http://localhost:8888/myserver/user/logout");
    // 创建参数队列
    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    formparams.add(new BasicNameValuePair("reqToken", reqToken));
    UrlEncodedFormEntity uefEntity;
    try {
      uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
      httppost.setEntity(uefEntity);
      System.out.println("executing request " + httppost.getURI());
      CloseableHttpResponse response = httpclient.execute(httppost);
      try {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          String str = EntityUtils.toString(entity, "UTF-8");
          if("1".equals(str)){
            isDel = true;
            return isDel;
          }
        }
      } finally {
        response.close();
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭连接,释放资源
      try {
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isDel;
  }
}
