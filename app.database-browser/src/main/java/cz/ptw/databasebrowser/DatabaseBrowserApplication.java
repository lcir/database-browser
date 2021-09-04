package cz.ptw.databasebrowser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cz.ptw.databasebrowser"})
public class DatabaseBrowserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseBrowserApplication.class, args);
    }

}
