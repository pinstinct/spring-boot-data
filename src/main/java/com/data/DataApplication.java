package com.data;

import com.data.domain.Aircraft;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class DataApplication {

	/*RedisOperations 타입인 빈을 생성하고, 구현체로 RedisTemplate 반환
	* */
	@Bean
	public RedisOperations<String, Aircraft> redisOperations (RedisConnectionFactory factory) {
		// 객체와 JSON 레코드 간 변환 시 사용할 Serializer 생성
		// Jackson: JSON 값의 마샬링/언마샬링(직렬화/역직렬화)
		Jackson2JsonRedisSerializer<Aircraft> serializer = new Jackson2JsonRedisSerializer<>(Aircraft.class);

		// String ID를 수용하기 위해 String 타입 키와 Aircraft 타입 값을 허용하는 RedisTemplate 생성
		RedisTemplate<String, Aircraft> template = new RedisTemplate<>();

		// RedisConnectionFactory 매개변수를 template 객체에 담아 레디스 DB에 커넥션을 생성하고 조회하게 됨
		template.setConnectionFactory(factory);
		template.setDefaultSerializer(serializer);

		// serializer는 Aircraft 타입의 객체를 기대하기 때문에 String 타입의 키를 변환하기 위해 다른 serializer 지정
		template.setKeySerializer(new StringRedisSerializer());

		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}

}
