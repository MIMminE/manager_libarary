package nuts.lib.manager.restdocs_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nuts.lib.manager.restdocs_manager.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.expression.FieldDescription;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

/**
 * This is a manager class that allows you to easily use the basic use of the RestDocs library on an annotation-based basis.
 * <p>
 * The basic manual is as follows:
 * <p>
 * 1. {@link RestDocsHolder} annotation specifies a Docs meta-information class that performs the request or response role.
 * <p>
 * 2. Use the {@link RestDocsSnippet} annotation and {@link FieldDescription} annotation to enter the document meta information.
 * <pre>
 * {@code
 * @RestDocsHolder(RestDocsHolder.RestDocsHolderType.request)
 * public class RequestRestDocs {
 *
 *     @RestDocsSnippet(fields = {
 *             @FieldDescription(name = "name", description = "멤버 이름"),
 *             @FieldDescription(name = "id", description = "요청 아이디"),
 *             @FieldDescription(name = "password", description = "요청 패스워드"),
 *             @FieldDescription(name = "contactNumber", description = "멤버 연락처"),
 *             @FieldDescription(name = "corporationId", description = "소속 기관 ID")
 *     })
 *     public Object createMember;
 *
 * }
 * }
 * </pre>
 * <p>
 * 3. If you specify the document meta information using an API such as {@link RestDocsManager#document(String, String)},
 * <p>
 * a restDocs document will be generated based on the success of mock mvc test
 *
 * <pre>
 * {@code
 * RestDocsManager restDocsManager = new RestDocsManager(RequestRestDocs.class, ResponseRestDocs.class);
 *
 * mockController.perform(MockMvcRequestBuilders.post("/api/v1/members")
 *                 .contentType(MediaType.APPLICATION_JSON)
 *                 .content(objectMapper.writeValueAsBytes(createMemberRequest)))
 *         .andExpect(status().isOk())
 *         .andDo(restDocsManager.document("request_member", "createMember", "createMemberResponse"));
 * }
 *
 * </pre>
 *
 * @since 2024. 07. 08
 */
public class RestDocsManager {

    private RestDocsFactory restDocsFactory = new RestDocsFactory();
    private final AnnotationProcessorDelegator annotationProcessorDelegator = AnnotationProcessorDelegator.INSTANCE;
    private final Class<?> requestHolder;
    private final Class<?> responseHolder;

    public RestDocsManager(Class<?> requestHolder, Class<?> responseHolder) {
        this.requestHolder = requestHolder;
        this.responseHolder = responseHolder;
    }

    public RestDocsManager(Class<?> responseHolder) {
        this.requestHolder = null;
        this.responseHolder = responseHolder;
    }

    public ResultHandler document(String documentName, String requestSnippetFieldName, String responseSnippetFieldName) {
        restDocsFactory.setDocumentName(documentName);
        restDocsFactory.setRequestFieldsSnippet(requestFieldsSnippet(requestSnippetFieldName));
        restDocsFactory.setResponseFieldsSnippet(responseFieldsSnippet(responseSnippetFieldName));
        return restDocsFactory.build();
    }

    public ResultHandler document(String documentName, String responseSnippetFieldName) {
        restDocsFactory.setDocumentName(documentName);
        restDocsFactory.setResponseFieldsSnippet(responseFieldsSnippet(responseSnippetFieldName));
        return restDocsFactory.build();
    }

    private RequestFieldsSnippet requestFieldsSnippet(String snippetFieldName) {

        if (requestHolder == null) throw new IllegalArgumentException("requestHolder is not injected.");

        try {
            if (requestHolder.getAnnotation(RestDocsHolder.class).value() != RestDocsHolder.RestDocsHolderType.request) {
                throw new IllegalArgumentException("There is no @RestDocsHolder annotation in the injected requestHolder.");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("There is no @RestDocsHolder annotation in the injected requestHolder.");
        }

        try {
            RestDocsSnippet docsSnippet = Objects.requireNonNull(requestHolder).getField(snippetFieldName).getAnnotation(RestDocsSnippet.class);
            List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
            Arrays.stream(docsSnippet.fields()).forEach(expressionField
                    -> annotationProcessorDelegator.handle(expressionField, fieldDescriptors));

            return PayloadDocumentation.requestFields(fieldDescriptors);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseFieldsSnippet responseFieldsSnippet(String snippetFieldName) {
        try {
            if (responseHolder.getAnnotation(RestDocsHolder.class).value() != RestDocsHolder.RestDocsHolderType.response) {
                throw new IllegalArgumentException("There is no @RestDocsHolder annotation in the injected responseHolder.");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("There is no @RestDocsHolder annotation in the injected responseHolder.");
        }

        try {
            RestDocsSnippet docsSnippet = responseHolder.getField(snippetFieldName).getAnnotation(RestDocsSnippet.class);
            List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
            Arrays.stream(docsSnippet.fields()).forEach(expressionField
                    -> annotationProcessorDelegator.handle(expressionField, fieldDescriptors));

            return PayloadDocumentation.responseFields(fieldDescriptors);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

}
