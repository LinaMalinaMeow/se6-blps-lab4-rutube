package black.orange.rutube.auth;

import black.orange.rutube.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(scripts = "classpath:sql/cleanDb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerTest extends BaseTest {

    @Test
    public void contractTest() throws Exception {
        String request = getExpectedRequest();
        ResultActions perform = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print());

        String expectedResponse = getExpectedResponse(perform);
        assertAll(
                () -> perform.andExpect(status().isOk())
        );
    }

    private String getExpectedRequest() throws Exception {
        return getStringFromFile("json/auth/request.json");

    }

    private String getExpectedResponse(ResultActions perform) throws Exception {
        return getStringFromFile("json/auth/response.json");

    }
}
