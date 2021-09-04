package cz.ptw.databasebrowser.databases.connection;

import cz.ptw.databasebrowser.databases.connection.exception.ConnectionNotFoundInDatabaseException;
import cz.ptw.databasebrowser.model.Connection;
import cz.ptw.databasebrowser.model.DatabaseType;
import cz.ptw.databasebrowser.view.rest.repository.ConnectionRestRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Abstract class for operations with JdbcHolder. This abstract should implement every service for accessing data
 * from managed connections.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDatabaseService {

    private final JdbcTemplateHolder connectionHolder;
    private final ConnectionRestRepository connectionRestRepository;
    protected final Map<String, DatabaseConfigurationDriver> drivers;

    /**
     * Method returns initialized JDBC template for connection ID
     *
     * @param connectionId Connection id -> primary key of Connection structure in db.
     * @return Initialized JDBC template for fetched connection.
     */
    protected JdbcTemplate getJdbcTemplate(final Integer connectionId) {
        try {
            return this.connectionHolder.getJdbcTemplate(connectionId);
        } catch (ConnectionNotFoundInDatabaseException e) {
            //Debug for a reason
            log.debug(e.getLocalizedMessage(), e);
        }
        this.createNewRecordInJdbcHolder(connectionId);
        return this.connectionHolder.getJdbcTemplate(connectionId);
    }

    /**
     * Method gets "DatabaseConfigurationDriver" that is bean with database query implementations. This method finds
     * the One of these drivers. For example, if in connection is configured mysql db, method returns mysql
     * driver implementation.
     *
     * @param connectionId Connection id -> primary key of Connection structure in db.
     * @return DatabaseConfigurationDriver of right type of db implementation
     */
    protected DatabaseConfigurationDriver getDatabaseConfigurationDriver(final Integer connectionId) {
        return drivers.get(this.getDatabaseType(connectionId).name());
    }

    private DatabaseType getDatabaseType(final Integer connectionId) {
        try {
            return this.connectionHolder.getDatabaseType(connectionId);
        } catch (ConnectionNotFoundInDatabaseException e) {
            //Debug for a reason
            log.debug(e.getLocalizedMessage(), e);
        }
        this.createNewRecordInJdbcHolder(connectionId);
        return this.connectionHolder.getDatabaseType(connectionId);
    }

    private void createNewRecordInJdbcHolder(final Integer connectionId) {
        final Connection connection = this.connectionRestRepository.findById(connectionId)
                .orElseThrow(
                        (Supplier<RuntimeException>) () -> new ConnectionNotFoundInDatabaseException("Finding connection for connectId: " + connectionId)
                );
        this.connectionHolder.addJdbcTemplate(connection, new JdbcTemplate(this.createNewDataSource(connection)));
    }

    @SneakyThrows
    private DataSource createNewDataSource(final Connection connection) {
        return new SimpleDriverDataSource(
                (Driver) Class.forName(drivers.get(connection.getDatabaseType().name()).getDatabaseDriverName()).newInstance(),
                "jdbc:" +
                        connection.getDatabaseType() + "://" +
                        connection.getHostname() + ":" +
                        connection.getPort() + "/" +
                        connection.getDatabaseName(),
                connection.getUsername(), connection.getPassword());
    }
}