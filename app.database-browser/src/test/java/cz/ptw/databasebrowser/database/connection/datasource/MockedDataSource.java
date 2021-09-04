package cz.ptw.databasebrowser.database.connection.datasource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import java.sql.Connection;

public class MockedDataSource extends AbstractDataSource {

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) {
        return null;
    }
}
