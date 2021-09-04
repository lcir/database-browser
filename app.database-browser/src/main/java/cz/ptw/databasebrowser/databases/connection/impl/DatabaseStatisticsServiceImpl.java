package cz.ptw.databasebrowser.databases.connection.impl;

import cz.ptw.databasebrowser.databases.connection.AbstractDatabaseService;
import cz.ptw.databasebrowser.databases.connection.DatabaseConfigurationDriver;
import cz.ptw.databasebrowser.databases.connection.DatabaseStatisticsService;
import cz.ptw.databasebrowser.databases.connection.JdbcTemplateHolder;
import cz.ptw.databasebrowser.view.rest.repository.ConnectionRestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatabaseStatisticsServiceImpl extends AbstractDatabaseService implements DatabaseStatisticsService {

    public DatabaseStatisticsServiceImpl(JdbcTemplateHolder connectionHolder, ConnectionRestRepository connectionRestRepository, Map<String, DatabaseConfigurationDriver> drivers) {
        super(connectionHolder, connectionRestRepository, drivers);
    }

    @Override
    public List<Map<String, Object>> listAllStatisticsForTablesQuery(final Integer connectionId) {
        return this.getJdbcTemplate(connectionId).queryForList(this.getDatabaseConfigurationDriver(connectionId).getListAllStatisticsForTablesQuery());
    }

    @Override
    public List<Map<String, Object>> listAllStatisticsForTableColumnsQuery(final Integer connectionId, final String schemaName, final String tableName) {
        final var listOfColumns = this.getJdbcTemplate(connectionId).queryForList(this.getDatabaseConfigurationDriver(connectionId).getListAllColumnNamesQuery(schemaName, tableName));
        final var columns = listOfColumns.stream()
                .filter(row -> row.containsKey("columnName"))
                .map(row -> row.get("columnName"))
                .map(Object::toString)
                .toList();

        return this.getJdbcTemplate(connectionId).queryForList(this.getDatabaseConfigurationDriver(connectionId).getListAllStatisticsForTableColumnsQuery(schemaName, tableName, columns));
    }
}
