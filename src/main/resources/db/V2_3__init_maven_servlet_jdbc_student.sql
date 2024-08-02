CREATE TABLE student
(
    student_id          SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    coordinator_id      INT             NOT NULL,
    PRIMARY KEY (student_id),
    UNIQUE (name),
    CONSTRAINT fk_student_coordinator
                FOREIGN KEY (coordinator_id)
                    REFERENCES coordinator (coordinator_id)
);