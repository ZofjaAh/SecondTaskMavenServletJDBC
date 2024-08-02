package com.aston.secondTask.infrastructure.repository.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudentSQLQuery {

    CREATE_STUDENT_WITH_COORDINATOR("""
            INSERT INTO student (name,  coordinator_id) 
            VALUES ((?),(?)) RETURNING id;  
            """),
    DELETE_STUDENT_BY_ID("""
             DELETE from student_course where student_id=(?);
             DELETE from coordinator where id=(?)\\gexec;
            """),
    GET_STUDENT_WITH_COURSES_BY_ID(""" 
                           select student.id, student.name, student.coordinator_id,
                           course.id AS course_id, course.name as course_name
                           from student LEFT OUTER JOIN student_course ON student.id = student_course.student_id
                           LEFT OUTER JOIN course ON course.id = student_course.course_id
                           WHERE student.id = (?);
                           """),
    UPDATE_COORDINATOR_BY_ID("""
            UPDATE student set coordinator_id=(?) where id=(?);
            """),
    ADD_COURSE("""
            INSERT INTO student_course (student_id,  course_id) 
            VALUES ((?),(?)); 
            """);
    final String QUERY;

}
