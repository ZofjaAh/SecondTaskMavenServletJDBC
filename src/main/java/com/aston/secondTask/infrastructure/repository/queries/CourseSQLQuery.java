package com.aston.secondTask.infrastructure.repository.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CourseSQLQuery {
    CREATE_COURSE("""
            INSERT INTO course (name) 
            VALUES (?) RETURNING id;              
             """),
    DELETE_COURSE_BY_ID("""
             DELETE from student_course where course_id =(?);
             DELETE from course where id=(?)\\gexec;
            """),
    GET_ALL_COURSES("""
            SELECT id, name FROM course;
            """),
    UPDATE_COURSE_NAME_BY_ID("""
            UPDATE course set name=(?) where id=(?);
            """);

    final String QUERY;

}
