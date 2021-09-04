package cz.ptw.databasebrowser.view.rest.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLRecoverableException;

/**
 * Interface for database controllers. Interface encapsulate default function as the exception handler for REST endpoints.
 * This interface should implement all application controllers for operations with external databases.
 */
public interface DatabaseControllerExceptionHandler {

    /**
     * Default function - exception handler for controllers
     *
     * @param exception Caught exception
     * @return Error Response entity
     */
    @ExceptionHandler({BadSqlGrammarException.class, SQLRecoverableException.class, CannotGetJdbcConnectionException.class, EmptyResultDataAccessException.class})
    default ResponseEntity<Object> errorHandler(Exception exception) {
        if (exception instanceof SQLRecoverableException || exception instanceof CannotGetJdbcConnectionException)
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);

        else if (exception instanceof EmptyResultDataAccessException)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
