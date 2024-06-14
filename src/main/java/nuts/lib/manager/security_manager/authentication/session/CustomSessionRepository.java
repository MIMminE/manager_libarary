package nuts.lib.manager.security_manager.authentication.session;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomSessionRepository implements SessionRepository<MapSession> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public MapSession createSession() {

        MapSession testSession = new MapSession("1");
        testSession.setMaxInactiveInterval(Duration.ofMillis(100));
        testSession.setAttribute("tester", 151315);
        return testSession;
    }

    @Override
    public void save(MapSession session) {

        redisTemplate.opsForHash().putAll(session.getId(), new HashMap<>() {{
            put("tester", session.getAttributeNames());
            put("SPRING_SECURITY_CONTEXT", session.getAttribute("SPRING_SECURITY_CONTEXT"));
        }});
    }

    @Override
    public MapSession findById(String id) {
        Map<Object, Object> entries = redisTemplate.opsForHash()
                .entries(id);
        System.out.println(entries + "!!!");
        MapSession mapSession = new MapSession(id);
        mapSession.setAttribute("SPRING_SECURITY_CONTEXT", entries.get("SPRING_SECURITY_CONTEXT"));

        return mapSession;
    }

    @Override
    public void deleteById(String id) {

    }
}
