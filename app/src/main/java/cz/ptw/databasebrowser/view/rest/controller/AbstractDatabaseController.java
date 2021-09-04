package cz.ptw.databasebrowser.view.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLRecoverableException;

public abstract class AbstractDatabaseController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler({BadSqlGrammarException.class, SQLRecoverableException.class, CannotGetJdbcConnectionException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Object> errorHandler(Exception e) {
        if (e instanceof SQLRecoverableException || e instanceof CannotGetJdbcConnectionException)
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);

        else if (e instanceof EmptyResultDataAccessException)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
