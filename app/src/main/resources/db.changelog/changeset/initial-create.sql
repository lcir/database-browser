CREATE TABLE connections
(
    id            INT          NOT NULL AUTO_INCREMENT,
    database_type VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    hostname      VARCHAR(255) NOT NULL,
    port          INT          NOT NULL,
    database_name VARCHAR(255) NOT NULL,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255),
    CONSTRAINT PK_Connections PRIMARY KEY (id)
);