package cz.ptw.databasebrowser.databases.connection;

import cz.ptw.databasebrowser.model.Connection;
import cz.ptw.databasebrowser.model.DatabaseType;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Connection holder interface
 */
public interface JdbcTemplateHolder {

    /**
     * Adds data source into connection Holder
     * @param connection Connection entity
     * @param jdbcTemplate jdbcTemplate of database
     */
    void addJdbcTemplate(final Connection connection, final JdbcTemplate jdbcTemplate);

    /**
     * Provide JDBC template of database
     * @param connectionId Connection entity ID
     * @return opened JDBC Template.
     */
    JdbcTemplate getJdbcTemplate(Integer connectionId);

    /**
     * Provide database type
     * @param connectionId Connection entity ID
     * @return database type.
     */
    DatabaseType getDatabaseType(Integer connectionId);
}
