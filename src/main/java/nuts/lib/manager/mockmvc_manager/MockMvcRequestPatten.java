package nuts.lib.manager.mockmvc_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class MockMvcRequestPatten {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static MockHttpServletRequestBuilder simplePost(String urlTemplate, Object requestObject) throws JsonProcessingException {
        return MockMvcRequestBuilders.post(urlTemplate)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(requestObject))
                .contentType(MediaType.APPLICATION_JSON);
    }
}
