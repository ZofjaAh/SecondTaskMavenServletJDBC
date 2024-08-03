package com.aston.secondTask.infrastructure.repository.queries;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SQLInitQuery {
    CREATE_DATABASE("""
            SELECT  'CREATE DATABASE maven_servlet_jdbc'
            WHERE  NOT  EXISTS ( SELECT  FROM pg_database WHERE datname = 'maven_servlet_jdbc' )\\gexec;
            """),

    CREATE_COORDINATOR_TABLE("""
            CREATE TABLE coordinator (
            coordinator_id  SERIAL NOT NULL,
            name VARCHAR(32) NOT NULL,
            PRIMARY KEY (coordinator_id),
            UNIQUE (name));
            """),
    CREATE_COURSE_TABLE("""
            CREATE TABLE course (
            course_id SERIAL NOT NULL,
            name  VARCHAR(32) NOT NULL,
            PRIMARY KEY (course_id),
            UNIQUE (name));
            """),
    CREATE_STUDENT_TABLE("""
            CREATE TABLE student (
            student_id SERIAL NOT NULL,
            name VARCHAR(32) NOT NULL,
            coordinator_id INT NOT NULL,
            PRIMARY KEY (student_id),
            UNIQUE (name),
            CONSTRAINT fk_student_coordinator
                FOREIGN KEY (coordinator_id)
                     REFERENCES coordinator (coordinator_id));
            """),
    CREATE_STUDENT_COURSE_TABLE("""
            CREATE TABLE student_course (
            id SERIAL NOT NULL,
            student_id INT NOT NULL,
            course_id INT NOT NULL,
            PRIMARY KEY (id),
            CONSTRAINT fk_student_course_student
                FOREIGN KEY (student_id)
                    REFERENCES student (student_id),
            CONSTRAINT fk_student_course_course
                 FOREIGN KEY (course_id)
                     REFERENCES course (course_id));
            """);
    final String QUERY;


}
