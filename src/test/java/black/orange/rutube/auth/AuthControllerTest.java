package black.orange.rutube.auth;

import black.orange.rutube.BaseTest;
import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.User;
import black.orange.rutube.repository.UserRepository;
import black.orange.rutube.service.RolesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:sql/cleanDb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerTest extends BaseTest {
    private final String DEFAULT_EMAIL = "alina2002180569@mail.ru";
    private final String DEFAULT_ENCODED_PASSWORD = "$2a$10$O18qD4/JpmkY1dagqefrQuuHWr3o/aLqygi.UAMCWq4HU.R3ton2e";

    @MockBean
    private RolesService rolesService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void registration_logicTest() throws Exception {
        String request = getExpectedRequest();

        doReturn(getUserDefaultRoles()).when(rolesService).getDefaultRoles();

        ResultActions perform = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print());

        assertAll(
                () -> perform.andExpect(status().isOk()),
                () -> perform.andExpect(header().stringValues("Content-Type", "text/plain;charset=UTF-8"))
        );
    }

    @Test
    public void auth_logicTest() throws Exception {
        String request = getExpectedRequest();
        doReturn(getRegisteredUser()).when(userRepository).findByEmail(DEFAULT_EMAIL);

        ResultActions perform = mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print());

        assertAll(
                () -> perform.andExpect(status().isOk()),
                () -> perform.andExpect(header().stringValues("Content-Type", "text/plain;charset=UTF-8"))
        );
    }

    private String getExpectedRequest() throws Exception {
        return getStringFromFile("json/auth/request.json");
    }

    private List<Role> getUserDefaultRoles() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return List.of(role);
    }

    private Optional<User> getRegisteredUser() {
        User user = new User();
        user.setEmail(DEFAULT_EMAIL);
        user.setPassword(DEFAULT_ENCODED_PASSWORD);
        user.setRoles(getUserDefaultRoles());
        return Optional.of(user);
    }
}
