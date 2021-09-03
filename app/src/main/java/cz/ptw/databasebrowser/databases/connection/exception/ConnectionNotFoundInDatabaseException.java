package cz.ptw.databasebrowser.databases.connection.exception;

/**
 * Generic exception for operation with Connections with JDBC Holder
 */
public class ConnectionNotFoundInDatabaseException extends RuntimeException {
    public ConnectionNotFoundInDatabaseException(String message) {
        super(message);
    }
}
