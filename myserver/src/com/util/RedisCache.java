package com.util;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCache {

  @Resource
  private RedisTemplate<String, String> redisTemplate;

  // 封装redis 缓存服务器服务接口

  private static String redisCode = "utf-8";

  /**
   * 通过key删除
   * 
   * @param key
   */
  public long del(final String... keys) {
    return redisTemplate.execute(new RedisCallback<Long>() {
      @Override
      public Long doInRedis(RedisConnection connection)
          throws DataAccessException {
        long result = 0;
        for (int i = 0; i < keys.length; i++) {
          result = connection.del(keys[i].getBytes());
        }
        return result;
      }
    });
  }

  /**
   * 添加key value 并且设置存活时间(byte)
   * 
   * @param key
   * @param value
   * @param liveTime
   */
  public void set(final byte[] key, final byte[] value, final long liveTime) {
    redisTemplate.execute(new RedisCallback<Object>() {
      @Override
      public Long doInRedis(RedisConnection connection)
          throws DataAccessException {
        connection.set(key, value);
        if (liveTime > 0) {
          connection.expire(key, liveTime);
        }
        return 1L;
      }
    });
  }

  /**
   * 添加key value 并且设置存活时间
   * 
   * @param key
   * @param value
   * @param liveTime
   */
  public void set(String key, String value, long liveTime) {
    this.set(key.getBytes(), value.getBytes(), liveTime);
  }

  /**
   * 添加key value
   * 
   * @param key
   * @param value
   */
  public void set(String key, String value) {
    this.set(key, value, 0L);
  }

  /**
   * 添加key value (字节)(序列化)
   * 
   * @param key
   * @param value
   */
  public void set(byte[] key, byte[] value) {
    this.set(key, value, 0L);
  }

  /**
   * 通过key获取redis value (String)
   * 
   * @param key
   * @return
   */
  public String get(final String key) {
    return redisTemplate.execute(new RedisCallback<String>() {
      @Override
      public String doInRedis(RedisConnection connection)
          throws DataAccessException {
        try {
          return new String(connection.get(key.getBytes()), redisCode);
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        return "";
      }
    });
  }

  /**
   * 通过正则匹配keys
   * 
   * @param pattern
   * @return
   */
  @SuppressWarnings("rawtypes")
  public Set keys(String pattern) {
    return redisTemplate.keys(pattern);

  }

  /**
   * 检查key是否已经存在
   * 
   * @param key
   * @return
   */
  public boolean exists(final String key) {
    return redisTemplate.execute(new RedisCallback<Boolean>() {
      @Override
      public Boolean doInRedis(RedisConnection connection)
          throws DataAccessException {
        return connection.exists(key.getBytes());
      }
    });
  }

  /**
   * 清空redis 所有数据
   * 
   * @return
   */
  public String flushDB() {
    return redisTemplate.execute(new RedisCallback<String>() {
      @Override
      public String doInRedis(RedisConnection connection)
          throws DataAccessException {
        connection.flushDb();
        return "ok";
      }
    });
  }

  /**
   * 查看redis里有多少数据
   * 
   * @return
   */
  public long dbSize() {
    return redisTemplate.execute(new RedisCallback<Long>() {
      @Override
      public Long doInRedis(RedisConnection connection)
          throws DataAccessException {
        return connection.dbSize();
      }
    });
  }

  /**
   * 检查redis是否连接成功
   * 
   * @return
   */
  public String ping() {
    return redisTemplate.execute(new RedisCallback<String>() {
      @Override
      public String doInRedis(RedisConnection connection)
          throws DataAccessException {

        return connection.ping();
      }
    });
  }

  /**
   * 添加缓存数据
   * 
   * @param key
   * @param obj
   * @param <T>
   * @return
   * @throws Exception
   */
  public <T> boolean putCacheTest(String key, T obj) throws Exception {
    final byte[] bkey = key.getBytes();
//    final byte[] bvalue = SerializerUtil.serializeObj(obj);

    final byte[] bvalue = redisTemplate.getStringSerializer().serialize((String) obj);
  
    boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
      @Override
      public Boolean doInRedis(RedisConnection connection)
          throws DataAccessException {
        return connection.setNX(bkey, bvalue);
      }
    });
    return result;
  }

  /**
   * 向redis数据库添加set的key value，并设置存活时间。
   * @param key
   * @param value
   * @return
   */
  public Long addMember(final String key, final String value, final long liveTime){
    return redisTemplate.execute(new RedisCallback<Long>() {
      @Override
      public Long doInRedis(RedisConnection connection)
          throws DataAccessException {
            connection.sAdd(key.getBytes(), value.getBytes());
            if (liveTime > 0) {
              connection.expire(key.getBytes(), liveTime);
            }
            return 1L;
      }
    });
  }
  
  /**
   * 判断redis数据库set中是否有某个value值
   * @param key
   * @param value
   * @return
   */
  public Boolean hasMember(final String key, final String value){
    return redisTemplate.execute(new RedisCallback<Boolean>() {
      @Override
      public Boolean doInRedis(RedisConnection connection)
          throws DataAccessException {
        return connection.sIsMember(key.getBytes(), value.getBytes());
      }
    });
  }
  
  /**
   * 判断redis数据库中是否有某个key值
   * @param key
   * @return
   */
  public Boolean hasKey(String key){
    return redisTemplate.hasKey(key);
  }
 
}