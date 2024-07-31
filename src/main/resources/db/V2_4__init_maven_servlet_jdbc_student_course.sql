CREATE TABLE student_course
(
    student_id      INT          NOT NULL,
    course_id       INT          NOT NULL,
    PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_student_course_student
        FOREIGN KEY (student_id)
            REFERENCES student (student_id),
    CONSTRAINT fk_student_course_course
            FOREIGN KEY (course_id)
                REFERENCES course (course_id)
);