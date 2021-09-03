package cz.ptw.databasebrowser.databases.connection.impl;

import cz.ptw.databasebrowser.databases.connection.DatabaseStructureInformationService;
import cz.ptw.databasebrowser.databases.connection.exception.ConnectionNotFoundInDatabaseException;
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
class DatabaseStructureInformationServiceImplTest {

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.26");

    @Autowired
    private DatabaseStructureInformationService databaseStructureInformationService;
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
    @DisplayName("Should fetch At Least Two Schema From Database when fetching Is From Test Container.")
    void should_fetchAtLeastTwoSchemaFromDatabase_when_fetchingIsFromTestContainer() {
        final var listAllSchemaInDatabase = databaseStructureInformationService.listAllSchemaInDatabase(connection.getId());

        assertNotNull(listAllSchemaInDatabase);
        assertEquals(2, listAllSchemaInDatabase.size());
    }

    @Test
    @DisplayName("Should fetch At Least One Table From Database when fetching Is From Test Container And Schema.")
    void should_fetchAtLeastOneTableFromDatabase_when_fetchingIsFromTestContainerAndSchema() {
        final var listAllTablesInDatabase = databaseStructureInformationService.listAllTablesInSchema(connection.getId(), connection.getDatabaseName());

        assertNotNull(listAllTablesInDatabase);
        assertTrue(listAllTablesInDatabase.size() > 0);
    }

    @Test
    @DisplayName("Should fetch At Least One Column From Table when fetching Is From Test Container Schema And Table Name Provided.")
    void should_fetchAtLeastOneTableFromDatabase_when_fetchingIsFromTestContainerSchemaAndTableNameProvided() {
        final var listAllColumnsInTable = databaseStructureInformationService.listAllColumnsInTable(connection.getId(), connection.getDatabaseName(), "TABLES");

        assertNotNull(listAllColumnsInTable);
        assertTrue(listAllColumnsInTable.size() > 0);
    }

    @Test
    @DisplayName("Should fetch Data Example From Table when connection Schema Table Are Provided.")
    void should_fetchDataExampleFromTable_when_connectionSchemaTableAreProvided() {
        final var dataExampleFromTable = databaseStructureInformationService.getDataExampleFromTable(connection.getId(), connection.getDatabaseName(), "TABLES");

        assertNotNull(dataExampleFromTable);
        assertTrue(dataExampleFromTable.size() > 0);
    }

    @Test
    @DisplayName("Should throw Exception when connectionId Is Not In Database.")
    void should_throwException_when_connectionIdIsNotInDatabase() {
        assertThrows(ConnectionNotFoundInDatabaseException.class, () ->
                databaseStructureInformationService.getDataExampleFromTable(-1, connection.getDatabaseName(), "TABLES"));

    }
}