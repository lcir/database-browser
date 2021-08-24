package cz.ptw.databasebrowser;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MySQLContainer;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@SpringBootTest
class DatabaseBrowserApplicationTests {

    @Rule
    private MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.26");

    @Autowired
    private JdbcTemplate template;



    @Test
    void contextLoads() throws SQLException {
            mysql.start();
        DatabaseMetaData md = template.getDataSource().getConnection().getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            System.out.println(rs.getString(3));
        }
    }



}
