package cz.ptw.databasebrowser.databases.connection.impl;

import cz.ptw.databasebrowser.databases.connection.DatabaseStatisticsService;
import cz.ptw.databasebrowser.model.Connection;
import cz.ptw.databasebrowser.model.DatabaseType;
import cz.ptw.databasebrowser.view.rest.repository.ConnectionRestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class MysqlDatabaseStatisticsServiceImplTest {

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.26");

    @Autowired
    private DatabaseStatisticsService databaseStatisticsService;
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
    @DisplayName("Should fetch Statistics For Table when containerId Is Well Provided.")
    void should_fetchStatisticsForTable_when_containerIdIsWellProvided() {
        final var listAllStatisticsForTablesQuery = databaseStatisticsService.listAllStatisticsForTablesQuery(connection.getId());

        assertNotNull(listAllStatisticsForTablesQuery);
        assertTrue( listAllStatisticsForTablesQuery.size() > 0);
    }

    @Test
    @DisplayName("Should fetch Statistics For Columns when containerId Schema Table Is Well Provided.")
    void should_fetchStatisticsForColumns_when_containerIdSchemaAndTableIsWellProvided() {
        final var listAllStatisticsForTableColumnsQuery = databaseStatisticsService.listAllStatisticsForTableColumnsQuery(connection.getId(), connection.getDatabaseName(), "TABLES");

        assertNotNull(listAllStatisticsForTableColumnsQuery);
        assertTrue( listAllStatisticsForTableColumnsQuery.size() > 0);
    }
}