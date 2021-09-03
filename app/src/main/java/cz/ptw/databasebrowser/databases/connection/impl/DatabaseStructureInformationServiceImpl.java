package cz.ptw.databasebrowser.databases.connection.impl;

import cz.ptw.databasebrowser.databases.connection.AbstractDatabaseService;
import cz.ptw.databasebrowser.databases.connection.DatabaseConfigurationDriver;
import cz.ptw.databasebrowser.databases.connection.DatabaseStructureInformationService;
import cz.ptw.databasebrowser.databases.connection.JdbcTemplateHolder;
import cz.ptw.databasebrowser.view.rest.repository.ConnectionRestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatabaseStructureInformationServiceImpl extends AbstractDatabaseService implements DatabaseStructureInformationService {

    public DatabaseStructureInformationServiceImpl(JdbcTemplateHolder connectionHolder, ConnectionRestRepository connectionRestRepository, Map<String, DatabaseConfigurationDriver> drivers) {
        super(connectionHolder, connectionRestRepository, drivers);
    }

    @Override
    public List<Map<String, Object>> listAllSchemaInDatabase(final Integer connectionId) {
        return this.getJdbcTemplate(connectionId).queryForList(this.getDatabaseConfigurationDriver(connectionId).getListAllSchemaQuery());
    }

    @Override
    public List<Map<String, Object>> listAllTablesInSchema(final Integer connectionId, final String schemaName) {
        return this.getJdbcTemplate(connectionId).queryForList(this.getDatabaseConfigurationDriver(connectionId).getListAllTablesQuery(schemaName));
    }

    @Override
    public List<Map<String, Object>> listAllColumnsInTable(final Integer connectionId, final String schemaName, final String tableName) {
        return this.getJdbcTemplate(connectionId).queryForList(this.getDatabaseConfigurationDriver(connectionId).getListAllColumnsQuery(schemaName, tableName));
    }

    @Override
    public Map<String, Object> getDataExampleFromTable(final Integer connectionId, final String schemaName, final String tableName) {
        return this.getJdbcTemplate(connectionId).queryForMap(this.getDatabaseConfigurationDriver(connectionId).getTableDataExampleQuery(schemaName, tableName));
    }

}
