package cz.ptw.databasebrowser.databases.connection;

import java.util.List;

/**
 * Interface is used for implementation database kind specific queries.
 */
public interface DatabaseConfigurationDriver {

    /**
     * Method returns class of JDBC Driver
     *
     * @return String written class of JDBC Driver
     */
    String getDatabaseDriverName();

    /**
     * Method must return string with query for querying all schema in database.
     *
     * @return Schema select as a string.
     */
    String getListAllSchemaQuery();

    /**
     * Method must return string with query for querying all tables in schema.
     *
     * @param schema Schema name
     * @return Query for tables as a string.
     */
    String getListAllTablesQuery(String schema);

    /**
     * Method must return string with query for querying all columns in table and information about columns.
     *
     * @param schema Schema name
     * @param table  Table name
     * @return Query for columns of table as a string.
     */
    String getListAllColumnsQuery(String schema, String table);

    /**
     * Method must return string with query for querying all column names.
     *
     * @param schema Schema name
     * @param table  Table name
     * @return Query for column names tables as a string.
     */
    String getListAllColumnNamesQuery(String schema, String table);

    /**
     * Method must return string with query for querying example of table data.
     *
     * @param schema Schema name
     * @param table  Table name
     * @return Query for column names tables as a string.
     */
    String getTableDataExampleQuery(String schema, String table);

    /**
     * Method must return string with query for querying all statistics of table.
     *
     * @return Query for statistics for tables.
     */
    String getListAllStatisticsForTablesQuery();

    /**
     * Method must return string with query for querying all statistics of columns in table.
     *
     * @param schema  Schema name
     * @param table   Table name
     * @param columns List of column names
     * @return Query for statistics for table columns.
     */
    String getListAllStatisticsForTableColumnsQuery(String schema, String table, List<String> columns);
}
