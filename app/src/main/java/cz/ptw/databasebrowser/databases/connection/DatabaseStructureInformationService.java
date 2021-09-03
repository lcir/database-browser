package cz.ptw.databasebrowser.databases.connection;

import java.util.List;
import java.util.Map;

/**
 * Database structure information service is service with basic operations for providing structure information.
 */
public interface DatabaseStructureInformationService {

    /**
     * Method is for listing all schemas in database.
     *
     * @param connectionId connection if from Connection table
     * @return List of maps - One list row per database row. And Map of attribute names and values.
     */
    List<Map<String, Object>> listAllSchemaInDatabase(Integer connectionId);

    /**
     * Method is designed for listing all tables in selected schema.
     *
     * @param connectionId connection if from Connection table
     * @param schemaName   Selected schema name
     * @return List of maps - One list row per database row. And Map of attribute names and values.
     */
    List<Map<String, Object>> listAllTablesInSchema(Integer connectionId, String schemaName);

    /**
     * Method js for listing all columns and technical information about columns from selected table.
     *
     * @param connectionId connection if from Connection table
     * @param schemaName   Selected schema name
     * @param tableName    Selected table name
     * @return List of maps - One list row per database row. And Map of attribute names and values.
     */
    List<Map<String, Object>> listAllColumnsInTable(Integer connectionId, String schemaName, String tableName);

    /**
     * Method returns example of table data from selected table. Method returns only one database row.
     *
     * @param connectionId connection if from Connection table
     * @param schemaName   Selected schema name
     * @param tableName    Selected table name
     * @return List of maps - Map of attribute names and values.
     */
    Map<String, Object> getDataExampleFromTable(Integer connectionId, String schemaName, String tableName);
}
