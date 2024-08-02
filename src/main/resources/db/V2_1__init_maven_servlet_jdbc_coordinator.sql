CREATE TABLE coordinator
(
    coordinator_id      SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    PRIMARY KEY (coordinator_id),
    UNIQUE (name)
);