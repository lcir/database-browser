package cz.ptw.databasebrowser.databases.connection.mysql;

import cz.ptw.databasebrowser.databases.connection.DatabaseConfigurationDriver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("mysql")
public class MysqlDatabaseConfigurationDriver implements DatabaseConfigurationDriver {

    @Override
    public String getDatabaseDriverName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public String getListAllSchemaQuery() {
        return "SHOW SCHEMAS;";
    }

    @Override
    public String getListAllTablesQuery(final String schema) {
        return "SHOW TABLES FROM %s;".formatted(schema);
    }

    @Override
    public String getListAllColumnsQuery(final String schema, final String table) {
        return """
                SHOW COLUMNS FROM %s.%s;
                """.formatted(schema, table);
    }

    @Override
    public String getListAllColumnNamesQuery(final String schema, final String table) {
        return """
                SELECT COLUMN_NAME as columnName FROM INFORMATION_SCHEMA.COLUMNS
                WHERE table_name = '%s'
                  AND table_schema =  '%s'
                """.formatted(table, schema);
    }

    @Override
    public String getTableDataExampleQuery(final String schema, final String table) {
        return "SELECT * FROM %s.%s LIMIT 1;".formatted(schema, table);
    }

    @Override
    public String getListAllStatisticsForTablesQuery() {
        return """
                SELECT t.TABLE_SCHEMA as schema_name, t.TABLE_NAME as table_name, t.TABLE_ROWS as items, count(c.COLUMN_NAME) as columns
                FROM INFORMATION_SCHEMA.TABLES t
                    JOIN INFORMATION_SCHEMA.COLUMNS c
                WHERE t.table_name = c.table_name
                  AND t.table_schema = c.table_schema
                GROUP BY t.TABLE_ROWS, t.TABLE_NAME, t.TABLE_SCHEMA
                                """;
    }

    @Override
    public String getListAllStatisticsForTableColumnsQuery(final String schema, final String table, final List<String> columns) {
        return """
                SELECT %s FROM %s.%s;
                 """.formatted(columns.stream()
                .map(column -> "avg(%s) as avg_%S, min(%s) as min_%s, max(%s) as max_%s"
                        .formatted(column, column, column, column, column, column)).collect(Collectors.joining(", ")), schema, table);
    }
}
