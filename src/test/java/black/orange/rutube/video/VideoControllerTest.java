package black.orange.rutube.video;

import black.orange.rutube.BaseTest;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(scripts = "classpath:sql/cleanDb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VideoControllerTest extends BaseTest {

    @Test
    @WithMockUser(roles = "USER")
    public void contractTest() throws Exception {
        String request = getExpectedRequest();
        ResultActions perform = mockMvc.perform(post("/user/video")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print());

        String expectedResponse = getExpectedResponse(perform);
        assertAll(
                () -> perform.andExpect(status().isOk()),
                () -> perform.andExpect(header().stringValues("Content-Type", MediaType.APPLICATION_JSON_VALUE)),
                () -> perform.andExpect(content().json(expectedResponse))
        );
    }

    private String getExpectedRequest() throws Exception {
        return getStringFromFile("json/video/request.json")
                .replaceFirst("NAME_REPLACE", "мулаточки")
                .replaceFirst("LINK_REPLACE", "мулаточки");
    }

    private String getExpectedResponse(ResultActions perform) throws Exception {
        String createdDate=JsonPath.read(perform.andReturn().getResponse().getContentAsString(), "$.created");
        String updatedDate=JsonPath.read(perform.andReturn().getResponse().getContentAsString(), "$.updated");
        return getStringFromFile("json/video/response.json")
                .replaceFirst("CREATED_DATE", createdDate)
                .replaceFirst("UPDATED_DATE", updatedDate);
    }
}
