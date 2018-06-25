package com.mall.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import com.google.gson.Gson;

public class RedisPool {

	private final static Logger _log = LoggerFactory.getLogger("RedisPool");
	
	static ShardedJedisPool pool;
	static ShardedJedis jedis;
	private static JedisPoolConfig config;// Jedis客户端池配置
	
	static{
		List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
		String servers = PropertiesUtils.getProperty("redis_server");
//		String servers = "redis://:test@10.10.38.33:6379/0";
		for(String server : servers.split(",")){
			if (server.indexOf("@") > -1){
				JedisShardInfo jedisShardInfo = new JedisShardInfo(server);
				list.add(jedisShardInfo);
			} else {
				String[] host = server.split(":", 2);
				JedisShardInfo jedisShardInfo = new JedisShardInfo(host[0], host[1]);
				list.add(jedisShardInfo);
			}
			
		}
		config = new JedisPoolConfig();
//		config.setMaxActive(60000);  //最大活动的对象个数
		config.setMaxTotal(60000);
		config.setMaxIdle(1000);     //对象最大空闲时间
//		config.setMaxWait(10000);
		config.setMaxWaitMillis(10000);
		config.setTestOnBorrow(true); // 归还连接时测试连接有效性
		pool =new ShardedJedisPool(config, list, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
		try{
			jedis = pool.getResource();
		}catch(Exception e){
			e.printStackTrace();
			_log.error("redis获取不到资源，重启");
		}
	}

	private static ShardedJedis getResource(){
		ShardedJedis jds = null;
		try{
			jds = pool.getResource();
		}catch(Exception e){
			_log.error("#RedisPool() getResource异常：",e);
			e.printStackTrace();
		}
		return jds;
	}
	
	/** ********************************************
	 * method name   : get 
	 * description   : 
	 * @return       : String
	 * @param        : @param key
	 * @param        : @return
	 * modified      : 樊宁 ,  2014-3-3  下午4:04:30
	 * @see          : 
	 * ********************************************/      
	public static String get(String key){
		ShardedJedis jds = null;
		try {
			jds = getResource();
			if(jds.exists(key)){
				return jds.get(key);
			}
		}catch(Exception e){
			_log.error("#RedisPool() get异常：", e);
			if(jds != null){
	            pool.returnBrokenResource(jds);
	        }
			e.printStackTrace();
		}finally{
//		    pool.returnResource(jds);
			returnResource(pool,jds);
		}
		return null;
		
	}
	/** ********************************************
	 * method name   : set 
	 * description   : 
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param seconds
	 * @param        : @param value
	 * modified      : 樊宁 ,  2014-3-3  下午4:04:22
	 * @see          : 
	 * ********************************************/      
	public static void set(String key, int seconds, String value){
		ShardedJedis jds = null;
		try {
			jds = getResource();
			jds.setex(key, seconds, value);
		}catch(Exception e){
			_log.error("#RedisPool() set异常：",e);
			if(jds != null){
	            pool.returnBrokenResource(jds);
	        }
			e.printStackTrace();
		}finally{
//		    pool.returnResource(jds);
			returnResource(pool,jds);
		}
	}
	/**
	 * ********************************************
	 * method name   : getByte 
	 * description   : 
	 * @return       : byte[]
	 * @param        : @param key
	 * @param        : @return
	 * modified      : dmm ,  2014-3-28  下午2:13:26
	 * @see          : 
	 * *******************************************
	 */
	public static byte[] getByte(byte[] key){
		ShardedJedis jds = null;
		try {
			jds = getResource();
			if(jds.exists(key)){
				return jds.get(key);
			}
		}catch(Exception e){
			_log.error("#RedisPool() getByte异常：",e);
			e.printStackTrace();
		}finally{
			returnResource(pool,jds);
		}
		return null;
		
	}
	/**
	 * ********************************************
	 * method name   : setByte 
	 * description   : 
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param seconds
	 * @param        : @param value
	 * modified      : dmm ,  2014-3-28  下午2:08:57
	 * @see          : 
	 * *******************************************
	 */
	public static void setByte(byte[] key, int seconds, byte[] value){
		ShardedJedis jds = null;
		try {
			jds = getResource();
			jds.setex(key, seconds, value);
		}catch(Exception e){
			_log.error("#RedisPool() setByte异常：",e);
			e.printStackTrace();
		}finally{
			returnResource(pool,jds);
		}
	}
	
	public static void set(String key, int seconds, Object obj){
		Gson gson = new Gson();
		set(key, seconds, gson.toJson(obj));
	}
	
	/** ********************************************
	 * method name   : rpush 
	 * description   : 在list尾部扩展一个元素
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param obj
	 * modified      : 樊宁 ,  2014-3-14  下午2:18:48
	 * @see          : 
	 * ********************************************/      
	public static void rpush(String key, Object obj){
		ShardedJedis jds = null;
		Gson gson = new Gson();
		try {
			jds = getResource();
			jds.rpush(key, gson.toJson(obj));
		}catch(Exception e){
			_log.error("#RedisPool() rpush异常：",e);
			e.printStackTrace();
		}finally{
			returnResource(pool,jds);
		}
	}
	
	/** ********************************************
	 * method name   : rpush 
	 * description   : 
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param obj
	 * @param        : @param seconds
	 * modified      : 樊宁 ,  2014-3-14  下午3:10:09
	 * @see          : 
	 * ********************************************/      
	public static void rpush(String key, Object obj, int seconds){
		rpush(key, obj);
		expire(key, seconds);
	}
	
	/** ********************************************
	 * method name   : lrange 
	 * description   : 取得list在指定范围内的元素
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param start
	 * @param        : @param end
	 * modified      : 樊宁 ,  2014-3-14  下午2:55:32
	 * @see          : 
	 * ********************************************/      
	public static List<String> lrange(String key, long start, long end){
		ShardedJedis jds = null;
		List<String> list = new ArrayList<String>();
		try {
			jds = getResource();
			list = jds.lrange(key, start, end);
		}catch(Exception e){
			_log.error("#RedisPool() lrange异常：",e);
			e.printStackTrace();
		}finally{
			returnResource(pool,jds);
		}
		return list;
	}
	
	/** ********************************************
	 * method name   : expire 
	 * description   : 设置过期时间
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param seconds
	 * modified      : 樊宁 ,  2014-3-14  下午2:54:09
	 * @see          : 
	 * ********************************************/      
	public static void expire(String key, int seconds){
		ShardedJedis jds = null;
		try{
			jds = getResource();
			jds.expire(key, seconds);
		}catch(Exception e){
			_log.error("#RedisPool() expire异常：",e);
			e.printStackTrace();
		}finally{
			returnResource(pool,jds);
		}
	}
	
	/** ********************************************
	 * method name   : del 
	 * description   : 
	 * @return       : void
	 * @param        : @param key
	 * modified      : 樊宁 ,  2014-3-3  下午4:04:17
	 * @see          : 
	 * ********************************************/      
	public static void del(String key){
		ShardedJedis jds = null;
		try {
			jds = getResource();
			if(jds.exists(key)){
				jds.del(key);
			}
		}catch(Exception e){
			_log.error("#RedisPool() del异常：",e);
			e.printStackTrace();
		}finally{
//		    pool.returnResource(jds);	
		    returnResource(pool,jds);
		}
	}
	
    
	/** ********************************************
	 * method name   : returnResource 
	 * description   : 返还到连接池
	 * @return       : void
	 * @param        : @param pool
	 * @param        : @param redis
	 * modified      : 樊宁 ,  2014-3-4  上午10:33:12
	 * @see          : 
	 * ********************************************/      
	public static void returnResource(ShardedJedisPool pool, ShardedJedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}

	/** ********************************************
	 * method name   : incr 
	 * description   : 
	 * @return       : Long
	 * @param        : @param key
	 * @param        : @return
	 * modified      : 樊宁 ,  2014-3-4  下午2:26:38
	 * @see          : 
	 * ********************************************/      
	public static Long incr(String key) {
		Long result = null;
		ShardedJedis jds = null;
		try {
			jds = pool.getResource();
			if(jds.exists(key)){
				result = jds.incr(key);
			}
		} catch (Exception e) {
			_log.error("#RedisPool() incr异常：", e);
			e.printStackTrace();
		} finally {
			returnResource(pool, jds);
		}
		return result;
	}
	
	/** ********************************************
	 * method name   : hset 
	 * description   : 
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param field
	 * @param        : @param value
	 * modified      : 樊宁 ,  2014-3-17  下午2:24:40
	 * @see          : 
	 * ********************************************/      
	private static void hset(String key, String field, String value) {
		ShardedJedis jds = null;
		try {
			jds = pool.getResource();
			jds.hset(key, field, value);
		} catch (Exception e) {
			_log.error("#RedisPool() hset异常：", e);
			e.printStackTrace();
		} finally {
			returnResource(pool, jds);
		}
	}
	
	/** ********************************************
	 * method name   : hset 
	 * description   : 
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param field
	 * @param        : @param value
	 * @param        : @param seconds 失效时间(秒)
	 * modified      : 樊宁 ,  2014-3-17  下午2:26:43
	 * @see          : 
	 * ********************************************/      
	public static void hset(String key, String field, Object value, int seconds) {
		Gson gson = new Gson();
		String values= gson.toJson(value);
		hset(key, field, values);
		expire(key, seconds);
	}
	
	/** ********************************************
	 * method name   : hdel 
	 * description   : 
	 * @return       : void
	 * @param        : @param key
	 * @param        : @param fields
	 * modified      : 樊宁 ,  2014-3-17  下午2:28:19
	 * @see          : 
	 * ********************************************/      
	public static void hdel(String key, String fields){
		ShardedJedis jds = null;
		try {
			jds = pool.getResource();
			if(jds.exists(key)){
				jds.hdel(key, fields);
			}
		} catch (Exception e) {
			_log.error("#RedisPool() hdel异常：", e);
			e.printStackTrace();
		} finally {
			returnResource(pool, jds);
		}
	}
	
	/** ********************************************
	 * method name   : hgetall 
	 * description   : 
	 * @return       : Map<String,String>
	 * @param        : @param key
	 * @param        : @param fields
	 * @param        : @return
	 * modified      : 樊宁 ,  2014-3-17  下午2:34:54
	 * @see          : 
	 * ********************************************/      
	public static Map<String, String> hgetall(String key){
		ShardedJedis jds = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			jds = pool.getResource();
			if(jds.exists(key)){
				map = jds.hgetAll(key);
			}
			return map;
		} catch (Exception e) {
			_log.error("#RedisPool() hdel异常：", e);
			e.printStackTrace();
			return map;
		} finally {
			returnResource(pool, jds);
		}
	}
	
	/** ********************************************
	 * method name   : hget 
	 * description   : 
	 * @return       : String
	 * @param        : @param key
	 * @param        : @param fields
	 * @param        : @return
	 * modified      : 樊宁 ,  2014-3-17  下午2:39:31
	 * @see          : 
	 * ********************************************/      
	public static String hget(String key, String fields){
		ShardedJedis jds = null;
		String value = "";
		try {
			jds = pool.getResource();
			if(jds.exists(key)){
				value = jds.hget(key, fields);
			}
			return value;
		} catch (Exception e) {
			_log.error("#RedisPool() hdel异常：", e);
			e.printStackTrace();
			return value;
		} finally {
			returnResource(pool, jds);
		}
	}
	
	public void testBasicString() {
		for(int i=0; i<5; i++){
            ShardedJedis jds = null;
            try {
                jds = pool.getResource();
                //jds.setex(key, seconds, value)
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                pool.returnResource(jds);
            }
        }
	}
	public static void delKeys(String cacheType) {
		ShardedJedis jds = null;
		try {
			jds = getResource();
//		ShardedJedis jedis = RedisPool.getResource();
			Collection<Jedis> jedisC = jds.getAllShards();
			Iterator<Jedis> iter = jedisC.iterator();
			long count = 0;
			while (iter.hasNext()) {
				Jedis _jedis = iter.next();
				Set<String> keys = _jedis.keys("*" + cacheType + "*");
				if (keys.size() > 0) {
					count += _jedis.del(keys.toArray(new String[keys.size()]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * ********************************************
	 * method name   : incrBy 
	 * description   : 线程安全的累加
	 * @return       : Long
	 * @param        : @param key
	 * @param        : @param num
	 * @param        : @return
	 * modified      : xuhuafeng ,  2016-7-6  下午04:22:14
	 * *******************************************
	 */
	public static Long incrBy(String key, long num) {
		Long result = null;
		ShardedJedis jds = null;
		try {
			jds = pool.getResource();
			result = jds.incrBy(key, num);
		} catch (Exception e) {
			_log.error("#RedisPool() incrBy异常：", e);
			e.printStackTrace();
		} finally {
			returnResource(pool, jds);
		}
		return result;
	}
}
