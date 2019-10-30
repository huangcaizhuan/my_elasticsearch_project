package com.my.xunwu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 * session会话（用redis保存session,防止重启项目需要重新登录）
 * @author hcz
 * @date 2019.10.30
 * maxInactiveIntervalInSeconds 保存的秒数（86400s为一天）
 */
@Configurable
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=864000)
public class RedisSessionConfig {

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
		return new StringRedisTemplate(factory);
	}
}
