package cz.ptw.databasebrowser.databases.connection.impl;

import cz.ptw.databasebrowser.databases.connection.JdbcTemplateHolder;
import cz.ptw.databasebrowser.databases.connection.exception.ConnectionNotFoundInDatabaseException;
import cz.ptw.databasebrowser.model.Connection;
import cz.ptw.databasebrowser.model.DatabaseType;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("singleton")
public class JdbcTemplateHolderImpl implements JdbcTemplateHolder {

    private final Map<Integer, DatabaseMetadataRecord> mapOfJdbcTemplates = new ConcurrentHashMap<>();

    public void addJdbcTemplate(final Connection connection, final JdbcTemplate jdbcTemplate) {
        this.mapOfJdbcTemplates.put(connection.getId(), new DatabaseMetadataRecord(jdbcTemplate, connection.getDatabaseType()));
    }

    private Optional<DatabaseMetadataRecord> getDatabaseMetadataByConnectionId(final Integer connectionId) {
        return Optional.ofNullable(this.mapOfJdbcTemplates.get(connectionId));
    }

    @Override
    public JdbcTemplate getJdbcTemplate(final Integer connectionId) {
        return this.getDatabaseMetadataByConnectionId(connectionId).orElseThrow(() ->
                new ConnectionNotFoundInDatabaseException("Jdbc Template is not preset in memory for id %d".formatted(connectionId))
        ).jdbcTemplate();
    }

    @Override
    public DatabaseType getDatabaseType(final Integer connectionId) {
        return this.getDatabaseMetadataByConnectionId(connectionId).orElseThrow(() ->
                new ConnectionNotFoundInDatabaseException("Database type is not preset in memory for id %d".formatted(connectionId))
        ).databasetype();
    }

    record DatabaseMetadataRecord(JdbcTemplate jdbcTemplate, DatabaseType databasetype){}
}
