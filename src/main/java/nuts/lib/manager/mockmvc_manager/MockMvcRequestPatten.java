package nuts.lib.manager.mockmvc_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class MockMvcRequestPatten {
    private static final ObjectMapper mapper = new ObjectMapper();


    public static MockHttpServletRequestBuilder requestGet(String urlTemplate){
        return MockMvcRequestBuilders.get(urlTemplate);
    }

    public static MockHttpServletRequestBuilder requestPost(String urlTemplate){
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
