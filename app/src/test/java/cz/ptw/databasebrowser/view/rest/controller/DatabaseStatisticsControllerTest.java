package cz.ptw.databasebrowser.view.rest.controller;

import cz.ptw.databasebrowser.model.Connection;
import cz.ptw.databasebrowser.model.DatabaseType;
import cz.ptw.databasebrowser.view.rest.repository.ConnectionRestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class DatabaseStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.26");
    @Autowired
    private ConnectionRestRepository connectionRestRepository;

    private Connection connection;

    @BeforeEach
    void setUp() {
        connection = new Connection();
        connection.setName("Mocked");
        connection.setDatabaseName("information_schema");
        connection.setDatabaseType(DatabaseType.mysql);
        connection.setHostname(MY_SQL_CONTAINER.getHost());
        connection.setPort(MY_SQL_CONTAINER.getMappedPort(3306));
        connection.setPassword(MY_SQL_CONTAINER.getPassword());
        connection.setUsername(MY_SQL_CONTAINER.getUsername());

        connection = connectionRestRepository.save(connection);
    }

    @Test
    @DisplayName("Should return Ok State when get On List Endpoint.")
    public void should_returnOkState_when_getOnListEndpoint() throws Exception {
        this.mockMvc.perform(get("/api/v1/connections/%d/statistics/table".formatted(connection.getId())))
                .andExpect(status().isOk());
    }    

    @Test
    @DisplayName("Should returddn Ok State when get On List Endpoint.")
    public void should_returdnOkState_when_getOnListEndpoint() throws Exception {
        this.mockMvc.perform(get("/api/v1/connections/%d/statistics/schema/%s/table/%s/column".formatted(connection.getId(), connection.getDatabaseName(), "TABLES")))
                .andExpect(status().isOk());
    }

}