package black.orange.rutube.auth;

import black.orange.rutube.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Sql(scripts = "classpath:sql/cleanDb.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanDb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerTest extends BaseTest {

    @Test
    public void contractTest() throws Exception {
        String request = getExpectedRequest();
        ResultActions perform = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print());

//        String expectedResponse = getExpectedResponse(perform);
//        assertAll(
//                () -> perform.andExpect(status().isOk()),
//                () -> perform.andExpect(header().stringValues("Content-Type", MediaType.APPLICATION_JSON_VALUE)),
//                () -> perform.andExpect(content().json(expectedResponse))
//        );
    }

    private String getExpectedRequest() throws Exception {
        return getStringFromFile("json/auth/request.json");

    }

    private String getExpectedResponse(ResultActions perform) throws Exception {
        return getStringFromFile("json/auth/response.json");

    }
}
