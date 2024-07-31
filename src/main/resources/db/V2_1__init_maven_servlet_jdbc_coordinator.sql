CREATE TABLE coordinator
(
    coordinator_id      SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    user_id             INT             NOT NULL,
    PRIMARY KEY (coordinator_id),
    UNIQUE (name),
    CONSTRAINT fk_coordinator_user
               FOREIGN KEY (user_id)
                    REFERENCES maven_servlet_jdbc_user (user_id)
);