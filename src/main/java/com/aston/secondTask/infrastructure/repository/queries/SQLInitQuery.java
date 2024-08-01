package com.aston.secondTask.infrastructure.repository.queries;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SQLInitQuery {
    CREATE_DATABASE("""
            SELECT  'CREATE DATABASE maven_servlet_jdbc'
            WHERE  NOT  EXISTS ( SELECT  FROM pg_database WHERE datname = 'maven_servlet_jdbc' )\\gexec;
            """),
    CREATE_USER_TABLE("""
            CREATE TABLE maven_servlet_jdbc_user (
            user_id SERIAL NOT NULL,
            user_name VARCHAR(32) NOT NULL,
            email VARCHAR(32) NOT NULL,
            password VARCHAR(128) NOT NULL,
            PRIMARY KEY (user_id));
            """),
    CREATE_COORDINATOR_TABLE("""
            CREATE TABLE coordinator (
            coordinator_id  SERIAL NOT NULL,
            name VARCHAR(32) NOT NULL,
            user_id INT NOT NULL,
            PRIMARY KEY (coordinator_id),
            UNIQUE (name),
            CONSTRAINT fk_coordinator_user
                      FOREIGN KEY (user_id)
                            REFERENCES maven_servlet_jdbc_user (user_id));
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
            user_id  INT NOT NULL,
            coordinator_id INT NOT NULL,
            PRIMARY KEY (student_id),
            UNIQUE (name),
            CONSTRAINT fk_student_user
                 FOREIGN KEY (user_id)
                     REFERENCES maven_servlet_jdbc_user (user_id),
            CONSTRAINT fk_student_coordinator
                FOREIGN KEY (coordinator_id)
                     REFERENCES coordinator (coordinator_id));
            """),
    CREATE_STUDENT_COURSE_TABLE("""
            CREATE TABLE student_course (
            student_id INT NOT NULL,
            course_id INT NOT NULL,
            PRIMARY KEY (student_id, course_id),
            CONSTRAINT fk_student_course_student
                FOREIGN KEY (student_id)
                    REFERENCES student (student_id),
            CONSTRAINT fk_student_course_course
                 FOREIGN KEY (course_id)
                     REFERENCES course (course_id));
            """);
    final String QUERY;


}
