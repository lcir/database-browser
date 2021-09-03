package cz.ptw.databasebrowser.databases.connection.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Generic exception for operation with Connections with JDBC Holder
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConnectionNotFoundInDatabaseException extends RuntimeException {
    public ConnectionNotFoundInDatabaseException(String message) {
        super(message);
    }
}
