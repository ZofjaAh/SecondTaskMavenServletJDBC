CREATE TABLE student
(
    student_id          SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    user_id             INT             NOT NULL,
    coordinator_id      INT             NOT NULL,
    PRIMARY KEY (student_id),
    UNIQUE (name),
    CONSTRAINT fk_student_user
                FOREIGN KEY (user_id)
                    REFERENCES maven_servlet_jdbc_user (user_id),
    CONSTRAINT fk_student_coordinator
                FOREIGN KEY (coordinator_id)
                    REFERENCES coordinator (coordinator_id)

);