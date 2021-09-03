package cz.ptw.databasebrowser.view.rest.controller;

import cz.ptw.databasebrowser.databases.connection.DatabaseStatisticsService;
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

@Api(tags = {"Database Statistics"})
@RestController("databaseStatisticsController")
@RequestMapping("/api/v1/connections")
@RequiredArgsConstructor
public class DatabaseStatisticsController {

    private final DatabaseStatisticsService databaseStatisticsService;

    @GetMapping(value = "/{connectionId}/statistics/table", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> showTableStatistics(@PathVariable("connectionId") Integer connectionId) {
        final List<Map<String, Object>> listOfAllSchemaInDatabase = databaseStatisticsService.listAllStatisticsForTablesQuery(connectionId);

        return new ResponseEntity<>(listOfAllSchemaInDatabase, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{connectionId}/statistics/schema/{schema}/table/{table}/column", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> showTableColumnStatistics(
            @PathVariable("connectionId") Integer connectionId,
            @PathVariable("schema") String schema,
            @PathVariable("table") String table) {
        final List<Map<String, Object>> listOfAllSchemaInDatabase = databaseStatisticsService.listAllStatisticsForTableColumnsQuery(connectionId, schema, table);

        return new ResponseEntity<>(listOfAllSchemaInDatabase, HttpStatus.ACCEPTED);
    }
}
