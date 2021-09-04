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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class PostgreDatabaseStatisticsServiceImplTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:9.6.12");

    @Autowired
    private DatabaseStatisticsService databaseStatisticsService;
    @Autowired
    private ConnectionRestRepository connectionRestRepository;

    private Connection connection;

    @BeforeEach
    void setUp() {

        connection = new Connection();
        connection.setName("Mocked");
        connection.setDatabaseName(POSTGRE_SQL_CONTAINER.getDatabaseName());
        connection.setDatabaseType(DatabaseType.postgresql);
        connection.setHostname(POSTGRE_SQL_CONTAINER.getHost());
        connection.setPort(POSTGRE_SQL_CONTAINER.getMappedPort(5432));
        connection.setPassword(POSTGRE_SQL_CONTAINER.getPassword());
        connection.setUsername(POSTGRE_SQL_CONTAINER.getUsername());

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
        final var listAllStatisticsForTableColumnsQuery = databaseStatisticsService.listAllStatisticsForTableColumnsQuery(connection.getId(), "information_schema", "sql_languages");

        assertNotNull(listAllStatisticsForTableColumnsQuery);
        assertTrue( listAllStatisticsForTableColumnsQuery.size() > 0);
    }
}