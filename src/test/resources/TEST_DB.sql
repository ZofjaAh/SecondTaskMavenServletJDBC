CREATE TABLE coordinator
(
    coordinator_id      SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    PRIMARY KEY (coordinator_id),
    UNIQUE (name)
);
CREATE TABLE course
(
    course_id      SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    PRIMARY KEY (course_id),
    UNIQUE (name)
);
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
CREATE TABLE student_course
(
    id              SERIAL       NOT NULL,
    student_id      INT          NOT NULL,
    course_id       INT          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_student_course_student
        FOREIGN KEY (student_id)
            REFERENCES student (student_id),
    CONSTRAINT fk_student_course_course
            FOREIGN KEY (course_id)
                REFERENCES course (course_id)
);
INSERT INTO coordinator (coordinator_id, name) VALUES
(1, 'Shopen'),
(2, 'Mocart');
SELECT SETVAL('coordinator_coordinator_id_seq', 2);
INSERT INTO course (course_id, name) VALUES
(1, 'Play guitar'),
(2, 'Play pianoforte');
SELECT SETVAL('course_course_id_seq', 2);
INSERT INTO student (student_id, name) VALUES
(1, 'Ivan Goodhear', 2),
(2, 'Nikol BadVoice', 1),
(3, 'Ihor Nothear', 1);
SELECT SETVAL('student_student_id_seq', 3);
INSERT INTO student_course (id, student_id, course_id) VALUES
(1, 1, 2),
(2, 1, 1),
(3, 2, 1);
SELECT SETVAL('student_course_id_seq', 3);
