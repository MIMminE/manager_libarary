package nuts.lib.manager.security_manager.authentication.session.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;

public class RedisSessionManager {

    public static RedisSessionRepository redisSessionRepository(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        return new RedisSessionRepository(redisTemplate);
    }


    public static RedisSessionRepository redisSessionRepository(RedisStandaloneConfiguration redisStandaloneConfiguration) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        factory.start();

        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return new RedisSessionRepository(redisTemplate);
    }

    public static SessionRepositoryFilter<?> redisSessionRepositoryFilter(RedisSessionRepository redisSessionRepository) {
        return new SessionRepositoryFilter<>(redisSessionRepository);
    }

}
