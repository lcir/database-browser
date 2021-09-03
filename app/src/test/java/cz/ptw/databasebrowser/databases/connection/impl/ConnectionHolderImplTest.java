package cz.ptw.databasebrowser.databases.connection.impl;

import cz.ptw.databasebrowser.database.connection.datasource.MockedDataSource;
import cz.ptw.databasebrowser.databases.connection.JdbcTemplateHolder;
import cz.ptw.databasebrowser.databases.connection.exception.ConnectionNotFoundInDatabaseException;
import cz.ptw.databasebrowser.model.Connection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ConnectionHolderImplTest {

    @Autowired
    private JdbcTemplateHolder connectionHolder;
    private Connection connection;

    @BeforeEach
    void setUp() {
        connection = new Connection();
        connection.setDatabaseName("Mocked");
        connection.setId(0);
    }

    @Test
    @DisplayName("Should autowire NotNull ConnectionHolder")
    void should_autowireNotNullConnectionHolder() {
        assertNotNull(connectionHolder);
    }


    @Test
    @DisplayName("Should add And Get JdbcTemplate Into ConnectionHolder")
    void should_addAndGetJdbcTemplateIntoConnectionHolder() {
        this.connectionHolder.addJdbcTemplate(this.connection, new JdbcTemplate(new MockedDataSource()));
        final JdbcTemplate jdbcTemplate = this.connectionHolder.getJdbcTemplate(this.connection.getId());

        assertNotNull(jdbcTemplate);
    }

    @Test
    @DisplayName("Should throw exception when dataSource With Negative Id Is Not Present")
    void should_getOptionalJdbcTemplateFalse_when_dataSourceWithNegativeIdIsNotPresent() {
        Assertions.assertThrows(ConnectionNotFoundInDatabaseException.class, () -> this.connectionHolder.getJdbcTemplate(-1));
    }
}