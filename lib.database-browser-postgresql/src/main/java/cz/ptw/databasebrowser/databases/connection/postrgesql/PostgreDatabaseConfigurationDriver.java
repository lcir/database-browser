package cz.ptw.databasebrowser.databases.connection.postrgesql;

import cz.ptw.databasebrowser.databases.connection.DatabaseConfigurationDriver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("postgresql")
public class PostgreDatabaseConfigurationDriver implements DatabaseConfigurationDriver {

    @Override
    public String getDatabaseDriverName() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getListAllSchemaQuery() {
        return "SELECT schema_name FROM information_schema.schemata;";
    }

    @Override
    public String getListAllTablesQuery(final String schema) {
        return "SELECT * FROM pg_catalog.pg_tables WHERE schemaname = '%s';".formatted(schema);
    }

    @Override
    public String getListAllColumnsQuery(final String schema, String table) {
        return """
                SELECT * FROM information_schema.columns
                WHERE table_schema = '%s'
                   AND table_name = '%s';
                """.formatted(schema, table);
    }

    @Override
    public String getListAllColumnNamesQuery(final String schema, final String table) {
        return """
                SELECT COLUMN_NAME as columnName FROM information_schema.columns
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
                SELECT t.TABLE_SCHEMA as schema_name, t.TABLE_NAME as table_name,  pg_size_pretty(pg_table_size('"'||t.table_schema||'"."'||t.table_name||'"')) as items, count(c.COLUMN_NAME) as size
                FROM INFORMATION_SCHEMA.TABLES t
                         , INFORMATION_SCHEMA.COLUMNS c
                WHERE t.table_name = c.table_name
                  AND t.table_schema = c.table_schema
                GROUP BY t.TABLE_NAME, t.TABLE_SCHEMA
                                                """;
    }

    @Override
    public String getListAllStatisticsForTableColumnsQuery(final String schema, final String table, final List<String> columns) {
        return """
                SELECT %s FROM %s.%s;
                 """.formatted(columns.stream().map(column -> "min(%s) as min_%s, max(%s) as max_%s".formatted(column, column, column, column)).collect(Collectors.joining(", ")), schema, table);
    }
}
