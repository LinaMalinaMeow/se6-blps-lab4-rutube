package black.orange.rutube.admin;

import black.orange.rutube.BaseTest;
import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.repository.VideoRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(scripts = "classpath:sql/cleanDb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminControllerTest extends BaseTest {
    @MockBean
    private VideoRepository videoRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getVideosForReview_logicTest() throws Exception {

        doReturn(getVideos()).when(videoRepository).findAllByVideoStatus(any());

        ResultActions perform = mockMvc.perform(get("/admin"))
                .andDo(MockMvcResultHandlers.print());

        String expectedResponse = getExpectedResponse(perform);
        assertAll(
                () -> perform.andExpect(status().isOk()),
                () -> perform.andExpect(header().stringValues("Content-Type", MediaType.APPLICATION_JSON_VALUE)),
                () -> perform.andExpect(content().json(expectedResponse))
        );
    }


    private String getExpectedResponse(ResultActions perform) throws Exception {
        String createdDate = JsonPath.read(perform.andReturn().getResponse().getContentAsString(), "$.[0].created");
        String updatedDate = JsonPath.read(perform.andReturn().getResponse().getContentAsString(), "$.[0].updated");
        return getStringFromFile("json/admin/response.json")
                .replaceAll("CREATED_DATE", createdDate)
                .replaceAll("UPDATED_DATE", updatedDate);
    }

    private Video createVideo(Long id, Date date) {
        Video video = new Video();
        video.setId(id);
        video.setVideoStatus(VideoStatus.REVIEW);
        video.setName("meow");
        video.setLink("link" + id);
        video.setCreated(date);
        video.setUpdated(date);
        return video;
    }

    private List<Video> getVideos() {
        Date date = new Date();
        return List.of(createVideo(1L, date), createVideo(2L, date), createVideo(3L, date));
    }
}
