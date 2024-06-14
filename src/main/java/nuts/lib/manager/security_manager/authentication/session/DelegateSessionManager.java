package nuts.lib.manager.security_manager.authentication.session;

import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import java.util.HashMap;
import java.util.Map;

public class DelegateSessionManager {

    Map<String, Class<? extends SessionRepository<?>>> sessionRepositoryMap = new HashMap<>() {{
        put("redis", RedisSessionRepository.class);
    }};

    public void test() {
        RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();


        RedisSessionRepository redisSessionRepository = redisHttpSessionConfiguration.sessionRepository();
    }

}
