package com.open.capacity.redis.util;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年04月23日 下午20:01:06 类说明
 * redis工具类
 */
@Slf4j
public class RedisUtil {

	
	private StringRedisTemplate stringRedisTemplate;

	private HashOperations<String, String, String> hashOperations;
	
	/**
	 * json序列化方式
	 */
	private static GenericJackson2JsonRedisSerializer redisObjectSerializer = new GenericJackson2JsonRedisSerializer();

	/**
	 * 默认RedisObjectSerializer序列化
	 */
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * redis工具类
	 * @param redisTemplate
	 * @param stringRedisTemplate
	 * @param hashOperations
	 */
	public RedisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate,
			HashOperations<String, String, String> hashOperations) {
		this.redisTemplate = redisTemplate;
		this.stringRedisTemplate = stringRedisTemplate;
		this.hashOperations = hashOperations;
	}

	

	/**
	 * 获取消息
	 * @param topicGroup
	 * @param channel
	 * @return
	 */
	public String getMsg(String topicGroup, String channel) {
		return hashOperations.get(topicGroup, channel);
	}

	/**
	 * 删除消息
	 * @param topicGroup
	 * @param channel
	 * @return
	 */
	public boolean removeMsg(String topicGroup, String channel) {
		publishMsg(topicGroup, channel, StringUtils.EMPTY);

		return hashOperations.delete(topicGroup, channel) == 1;
	}
	/**
	 * 发送消息，存redis hash
	 * @param topicGroup
	 * @param channel
	 * @param msg
	 * @return
	 */
	public boolean publishMsg(String topicGroup, String channel, String msg) {
		hashOperations.put(topicGroup, channel, msg);
		//向通道发送消息的方法
		stringRedisTemplate.convertAndSend(topicGroup + "-" + channel, msg);

		return true;
	}
	/**
	 * 订阅回调
	 * @param msg
	 * @param redisSubscribeCallback
	 */
	public void subscribeConfig(String msg, RedisSubscribeCallback redisSubscribeCallback) {
		redisSubscribeCallback.callback(msg);
	}

	/**
	 * 指定缓存失效时间
	 *
	 * @param key
	 *            键
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	public boolean expire(String key, long time) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				long rawTimeout = TimeoutUtils.toMillis(time, TimeUnit.SECONDS);
				try {
					return connection.pExpire(key.getBytes(), rawTimeout);
				} catch (Exception e) {
					// Driver may not support pExpire or we may be running on
					// Redis 2.4
					return connection.expire(key.getBytes(), TimeoutUtils.toSeconds(rawTimeout, TimeUnit.SECONDS));
				}
			}
		});
	}

	/**
	 * 根据key 获取过期时间
	 *
	 * @param key
	 *            键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key) {

		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				try {
					return connection.pTtl(key.getBytes(), TimeUnit.SECONDS);
				} catch (Exception e) {
					// Driver may not support pTtl or we may be running on Redis
					// 2.4
					return connection.ttl(key.getBytes(), TimeUnit.SECONDS);
				}
			}
		});
	}

	/**
	 * 判断key是否存在
	 *
	 * @param key
	 *            键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key) {
		return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.exists(key.getBytes()));
	}

	// ============================Object=============================

	/**
	 * 普通缓存获取
	 *
	 * @param key
	 *            键
	 * @return 值
	 */
	public Object get(String key) {

		Object value = redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				// redis info
				byte[] temp = null;
				temp = connection.get(key.getBytes());
				connection.close();

				return redisObjectSerializer.deserialize(temp);
			}
		});

		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存放入
	 *
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return true成功 false失败
	 */
	public boolean set(String key, Object value) {
		try {

			redisTemplate.execute((RedisCallback<Long>) connection -> {
				// redis info
				byte[] values = redisObjectSerializer.serialize(value);
				connection.set(key.getBytes(), values);
				connection.close();

				return 1L;
			});
			return true;
		} catch (Exception e) {
			StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("set-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
			return false;
		}
	}

	/**
	 * 普通缓存放入并设置时间
	 *
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {

				redisTemplate.execute((RedisCallback<Long>) connection -> {
					// redis info
					byte[] values = redisObjectSerializer.serialize(value);
					connection.set(key.getBytes(), values);
					connection.expire(key.getBytes(), 60 * time);
					connection.close();
					return 1L;
				});

			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("set-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
			return false;
		}
	}

	/**
	 * 递增
	 *
	 * @param key
	 *            键
	 * @param delta
	 *            要增加几(大于0)
	 * @return
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.execute((RedisCallback<Long>) connection -> {

			return connection.incrBy(key.getBytes(), delta);
		});
	}

	/**
	 * 递减
	 *
	 * @param key
	 *            键
	 * @param delta
	 *            要减少几(小于0)
	 * @return
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}

		return redisTemplate.execute((RedisCallback<Long>) connection -> {

			return connection.incrBy(key.getBytes(), -delta);
		});

	} 

	/**
	 * 删除缓存
	 *
	 * @param key
	 *            可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param offset
	 *            位 8Bit=1Byte
	 * @return
	 */
	public boolean setBit(String key, long offset, boolean isShow) {
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.setBit(key, offset, isShow);
			result = true;
		} catch (Exception e) {
			StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("setBit-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
			return false ;
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param offset
	 * @return
	 */
	public boolean getBit(String key, long offset) {
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			result = operations.getBit(key, offset);
		} catch (Exception e) {
			StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("getBit-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
			return false ;
		}
		return result;
	}

	
	//===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lGet-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lGetListSize-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lGetIndex-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lSet-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lSet-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lSet-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lSet-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lUpdateIndex-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	     
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
        	StackTraceElement stackTraceElement= e.getStackTrace()[0];	 	                        
            log.error("lRemove-"+stackTraceElement.getMethodName()+"--"+stackTraceElement.getLineNumber());	 	                      
            return 0;
        }
    }
    
    
  // ================================Map=================================
    /**
	 * 哈希 添加
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hmSet(String key, Object hashKey, Object value) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
	}

	/**
	 * 哈希获取数据
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hmGet(String key, Object hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}

	 
	// ================================set=================================

	/**
	 * 集合添加
	 *
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		set.add(key, value);
	}

	/**
	 * 集合获取
	 *
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}

	/**
	 * 有序集合添加
	 *
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void zAdd(String key, Object value, double scoure) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	/**
	 * 有序集合获取
	 *
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		redisTemplate.opsForValue();
		return zset.rangeByScore(key, scoure, scoure1);
	}

	 

	/**
	 * 有序集合获取排名
	 *
	 * @param key
	 *            集合名称
	 * @param value
	 *            值
	 */
	public Long zRank(String key, Object value) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.rank(key, value);
	}

	/**
	 * 有序集合获取排名
	 *
	 * @param key
	 */
	public Set<ZSetOperations.TypedTuple<Object>> zRankWithScore(String key, long start, long end) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		Set<ZSetOperations.TypedTuple<Object>> ret = zset.rangeWithScores(key, start, end);
		return ret;
	}

	/**
	 * 有序集合添加
	 *
	 * @param key
	 * @param value
	 */
	public Double zSetScore(String key, Object value) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.score(key, value);
	}

	/**
	 * 有序集合添加分数
	 *
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void incrementScore(String key, Object value, double scoure) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.incrementScore(key, value, scoure);
	}

	/**
	 * 有序集合获取排名
	 *
	 * @param key
	 */
	public Set<ZSetOperations.TypedTuple<Object>> reverseZRankWithScore(String key, long start, long end) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		Set<ZSetOperations.TypedTuple<Object>> ret = zset.reverseRangeByScoreWithScores(key, start, end);
		return ret;
	}

	/**
	 * 有序集合获取排名
	 *
	 * @param key
	 */
	public Set<ZSetOperations.TypedTuple<Object>> reverseZRankWithRank(String key, long start, long end) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		Set<ZSetOperations.TypedTuple<Object>> ret = zset.reverseRangeWithScores(key, start, end);
		return ret;
	}
    
}
