package nuts.lib.manager.restdocs_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

/**
 * mockMvc API support class for ease of use.
 *
 * @since 2024. 07. 08
 */
public abstract class MockMvcSupport {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * You can use the {@link MockMvcSupport#mapMatchers(Map)} API to validate the results of mockMvc using a map structure.
     * <pre>
     * {@code
     * .andExpectAll(MockMvcManager.mapMatchers(Map.of(
     *     "id", requestId,
     *     "name", "tester",
     *     "contactNumber", "010-1234-5678",
     *     "corporationId", corporationId)))
     * }
     * </pre>
     *
     * @since 2024. 07. 08
     */
    public static ResultMatcher[] mapMatchers(Map<String, Object> mapMatcher) {
        return mapMatcher.entrySet().stream()
                .map(entry -> MockMvcResultMatchers.jsonPath(entry.getKey()).value(entry.getValue())).toArray(ResultMatcher[]::new);
    }

    public static MockHttpServletRequestBuilder requestGet(String urlTemplate) {
        return MockMvcRequestBuilders.get(urlTemplate);
    }

    public static MockHttpServletRequestBuilder requestPost(String urlTemplate) {
        return MockMvcRequestBuilders.post(urlTemplate);
    }

    public static MockHttpServletRequestBuilder requestPost(String urlTemplate, Object requestObject) throws JsonProcessingException {
        return MockMvcRequestBuilders.post(urlTemplate)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(requestObject))
                .contentType(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder requestDelete(String urlTemplate) {
        return MockMvcRequestBuilders.delete(urlTemplate);
    }

    public static MockHttpServletRequestBuilder requestDelete(String urlTemplate, Object requestObject) throws JsonProcessingException {
        return MockMvcRequestBuilders.delete(urlTemplate)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(requestObject))
                .contentType(MediaType.APPLICATION_JSON);
    }
}
