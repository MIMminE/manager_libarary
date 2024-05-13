package nuts.lib.manager.mockmvc_manager;

import lombok.RequiredArgsConstructor;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class MockMvcManager {

    public static ResultMatcher[] mapMatchers(Map<String, Object> mapMatcher) {
        return mapMatcher.entrySet().stream()
                .map(entry -> MockMvcResultMatchers.jsonPath(entry.getKey()).value(entry.getValue())).toArray(ResultMatcher[]::new);
    }

}
