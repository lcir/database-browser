package cz.ptw.databasebrowser.databases.connection;

import java.util.List;
import java.util.Map;

/**
 * Database structure information service is service with basic operations for providing statistics of tables and columns
 */
public interface DatabaseStatisticsService {

    /**
     * Method returns list with all statistics information of tables in specified connection.
     *
     * @param connectionId Connection id -> primary key of Connection structure in db.
     * @return List of maps - One list row per database row. And Map of attribute names and values.
     */
    List<Map<String, Object>> listAllStatisticsForTablesQuery(Integer connectionId);

    /**
     * Method returns list with all statistics information of columns in specified table.
     *
     * @param connectionId Connection id -> primary key of Connection structure in db.
     * @param schemaName   Selected schema name
     * @param tableName    Selected table name
     * @return List of maps - One list row per database row. And Map of attribute names and values.
     */
    List<Map<String, Object>> listAllStatisticsForTableColumnsQuery(Integer connectionId, String schemaName, String tableName);
}
