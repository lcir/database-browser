package cz.ptw.databasebrowser.view.rest;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.containers.MySQLContainer;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConnectionsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Rule
    private MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.26");



    @Test
    public void whenTestApp_thenEmptyResponse() throws Exception {
        this.mockMvc.perform(get("/api/v1/connections/"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].firstName", is("Ligza")));
    }
}
