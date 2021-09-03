package cz.ptw.databasebrowser.databases.connection;

import java.util.List;

public interface DatabaseConfigurationDriver {

    String getDatabaseDriverName();

    String getListAllSchemaQuery();

    String getListAllTablesQuery(String schema);

    String getListAllColumnsQuery(String schema, String table);

    String getListAllColumnNamesQuery(String schema, String table);

    String getTableDataExampleQuery(String schema, String table);

    String getListAllStatisticsForTablesQuery();

    String getListAllStatisticsForTableColumnsQuery(String schema, String table, List<String> columns);
}
