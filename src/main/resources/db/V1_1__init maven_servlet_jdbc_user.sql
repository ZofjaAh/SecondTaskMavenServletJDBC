CREATE TABLE maven_servlet_jdbc_user
(
    user_id     SERIAL          NOT NULL,
    user_name   VARCHAR(32)     NOT NULL,
    email       VARCHAR(32)     NOT NULL,
    password    VARCHAR(128)    NOT NULL,
    PRIMARY KEY (user_id)
);

