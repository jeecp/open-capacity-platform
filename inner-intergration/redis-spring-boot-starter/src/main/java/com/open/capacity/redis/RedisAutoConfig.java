package com.open.capacity.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.open.capacity.redis.serializer.RedisObjectSerializer;
import com.open.capacity.redis.util.RedisUtil;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年04月23日 下午20:01:06 类说明
 * redis自动装配
 */
@Configuration
@AutoConfigureBefore(RedisTemplate.class)
public class RedisAutoConfig {

	

	
	@Autowired(required=false)  
	private LettuceConnectionFactory lettuceConnectionFactory;

	/**
	 * 适配redis cluster单节点
	 * @return
	 */
	@Primary
	@Bean("redisTemplate")
	// 没有此属性就不会装配bean 如果是单个redis 将此注解注释掉
	@ConditionalOnProperty(name = "spring.redis.cluster.nodes", matchIfMissing = false)
	public RedisTemplate<String, Object> getRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);

		RedisSerializer stringSerializer = new StringRedisSerializer();
		// RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		redisTemplate.setKeySerializer(stringSerializer); // key的序列化类型
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(redisObjectSerializer); // value的序列化类型
		redisTemplate.setHashValueSerializer(redisObjectSerializer); // value的序列化类型
		redisTemplate.afterPropertiesSet();

		redisTemplate.opsForValue().set("hello", "wolrd");
		return redisTemplate;
	}
	/**
	 * 适配redis单节点
	 * @return
	 */
	@Primary
	@Bean("redisTemplate")
	@ConditionalOnProperty(name = "spring.redis.host", matchIfMissing = true)
	public RedisTemplate<String, Object> getSingleRedisTemplate( ) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer()); // key的序列化类型
		redisTemplate.setValueSerializer(redisObjectSerializer); // value的序列化类型
		redisTemplate.setHashValueSerializer(redisObjectSerializer); 
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	
    @Bean
    public HashOperations<String, String, String> hashOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForHash();
    }
	/**
	 * redis工具类
	 */
	@Bean("redisUtil")
	public RedisUtil redisUtil( RedisTemplate<String, Object>  redisTemplate ,StringRedisTemplate stringRedisTemplate,HashOperations<String, String, String> hashOperations) {
		RedisUtil redisUtil = new RedisUtil(redisTemplate ,stringRedisTemplate , hashOperations); 
		return redisUtil;
	}
	
}
