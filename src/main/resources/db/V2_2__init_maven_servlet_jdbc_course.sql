CREATE TABLE course
(
    course_id      SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    PRIMARY KEY (course_id),
    UNIQUE (name)
);