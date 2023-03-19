package black.orange.rutube;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BaseTest {
    @Autowired
    protected MockMvc mockMvc;

    public static String getStringFromFile(String filePath) throws IOException {
        ClassLoader classLoader = BaseTest.class.getClassLoader();
        String fileName = classLoader.getResource(filePath).getFile();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.ready()) {
                sb.append(br.readLine()).append("\n");
            }
        }
        return sb.toString();
    }
}
