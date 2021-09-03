package cz.ptw.databasebrowser.databases.connection.exception;

public class ConnectionNotFoundInDatabaseException extends RuntimeException {
    public ConnectionNotFoundInDatabaseException(String message) {
        super(message);
    }
}
