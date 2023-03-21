package black.orange.rutube.video;

import black.orange.rutube.BaseTest;
import black.orange.rutube.converter.VideoConverter;
import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.repository.VideoRepository;
import black.orange.rutube.service.UserService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(scripts = "classpath:sql/cleanDb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VideoControllerTest extends BaseTest {
    @MockBean
    private VideoRepository videoRepository;
    @MockBean
    private VideoConverter videoConverter;
    @MockBean
    private UserService userService;


    @Test
    @WithMockUser(roles = "USER")
    public void addVideo_logicTest() throws Exception {
        String request = getExpectedRequest();

        Optional<Video> video = createSavedVideo();
        video.get().setName("мулаточки");
        doReturn(1L).when(userService).getUserIdFromContext();
        doReturn(video.get()).when(videoConverter).toEntity(any(), any());
        doReturn(false).when(videoRepository).existsByLink(video.get().getLink());
        doReturn(video.get()).when(videoRepository).save(video.get());

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

    @Test
    @WithMockUser(roles = "USER")
    public void updateVideo_logicTest() throws Exception {
        String request = getExpectedRequest();

        Optional<Video> video = createSavedVideo();
        doReturn(1L).when(userService).getUserIdFromContext();
        doReturn(video).when(videoRepository).findByLinkAndUserId(video.get().getLink(), 1L);
        doReturn(video.get()).when(videoRepository).save(video.get());

        ResultActions perform = mockMvc.perform(put("/user/video")
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
        String createdDate = JsonPath.read(perform.andReturn().getResponse().getContentAsString(), "$.created");
        String updatedDate = JsonPath.read(perform.andReturn().getResponse().getContentAsString(), "$.updated");
        return getStringFromFile("json/video/response.json")
                .replaceFirst("CREATED_DATE", createdDate)
                .replaceFirst("UPDATED_DATE", updatedDate);
    }

    private Optional<Video> createSavedVideo() {
        Video video = new Video();
        Date date = new Date();
        video.setId(1L);
        video.setVideoStatus(VideoStatus.REVIEW);
        video.setName("test");
        video.setLink("мулаточки");
        video.setCreated(date);
        video.setUpdated(date);
        video.setUserId(1L);
        return Optional.of(video);
    }

}
