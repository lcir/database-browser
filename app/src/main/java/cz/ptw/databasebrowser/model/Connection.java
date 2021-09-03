package cz.ptw.databasebrowser.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Main connection entity
 */
@Entity(name = "Connections")
@Getter
@Setter
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Name cannot be null")
    private DatabaseType databaseType;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 0, max = 255)
    private String name;
    @NotEmpty(message = "Database hostname cannot be empty")
    @NotNull(message = "Database hostname cannot be null")
    @Size(min = 0, max = 255)
    private String hostname;
    @Min(value = 1, message = "Port value must be more then 0")
    @NotNull(message = "Database port cannot be null")
    private Integer port;
    @NotEmpty(message = "Database Name cannot be empty")
    @NotNull(message = "Database name cannot be null")
    @Size(min = 0, max = 255)
    private String databaseName;
    @NotEmpty(message = "Database username cannot be empty")
    @NotNull(message = "Database username cannot be null")
    @Size(min = 0, max = 255)
    private String username;
    @Size(max = 255)
    private String password;
}
