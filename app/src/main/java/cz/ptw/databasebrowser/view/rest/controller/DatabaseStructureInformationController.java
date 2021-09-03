package cz.ptw.databasebrowser.view.rest.controller;

import cz.ptw.databasebrowser.databases.connection.DatabaseStructureInformationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = {"Database Structure Information"})
@RestController("databaseStructureInformationController")
@RequestMapping("/api/v1/connections")
@RequiredArgsConstructor
public class DatabaseStructureInformationController {

    private final DatabaseStructureInformationService databaseSchemaService;

    @GetMapping(value = "/{connectionId}/schema", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> showSchemas(@PathVariable("connectionId") Integer connectionId) {
        final List<Map<String, Object>> listOfAllSchemaInDatabase = databaseSchemaService.listAllSchemaInDatabase(connectionId);

        return new ResponseEntity<>(listOfAllSchemaInDatabase, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{connectionId}/schema/{schema}/table", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> showTablesInSchema(
            @PathVariable("connectionId") Integer connectionId,
            @PathVariable("schema") String schemaName) {
        final List<Map<String, Object>> listOfAllSchemaInDatabase = databaseSchemaService.listAllTablesInSchema(connectionId, schemaName);

        return new ResponseEntity<>(listOfAllSchemaInDatabase, HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "/{connectionId}/schema/{schema}/table/{table}/column", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> showColumnsInTable(
            @PathVariable("connectionId") Integer connectionId,
            @PathVariable("schema") String schemaName,
            @PathVariable("table") String tableName) {
        final List<Map<String, Object>> listOfAllSchemaInDatabase = databaseSchemaService.listAllColumnsInTable(connectionId, schemaName, tableName);

        return new ResponseEntity<>(listOfAllSchemaInDatabase, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{connectionId}/schema/{schema}/table/{table}/example", produces = "application/json")
    public ResponseEntity<Map<String, Object>> showExampleOfTableData(
            @PathVariable("connectionId") Integer connectionId,
            @PathVariable("schema") String schemaName,
            @PathVariable("table") String tableName) {
        final Map<String, Object> listOfAllSchemaInDatabase = databaseSchemaService.getDataExampleFromTable(connectionId, schemaName, tableName);

        return new ResponseEntity<>(listOfAllSchemaInDatabase, HttpStatus.ACCEPTED);
    }
}
