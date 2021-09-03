package cz.ptw.databasebrowser.databases.connection;

import java.util.List;
import java.util.Map;

/**
 * Database structure information service is service with basic operations for providing structure information.
 */
public interface DatabaseStatisticsService {

    List<Map<String, Object>> listAllStatisticsForTablesQuery(Integer connectionId);

    List<Map<String, Object>> listAllStatisticsForTableColumnsQuery(Integer connectionId, String schemaName, String tableName);
}
