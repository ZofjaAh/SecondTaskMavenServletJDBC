package com.aston.secondTask.infrastructure.repository.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoordinatorSQLQuery {
    CREATE_COORDINATOR("""
            INSERT INTO coordinator (name) 
            VALUES (?) RETURNING coordinator_id;              
             """),
    GET_COORDINATOR_WITH_STUDENTS_BY_ID("""
            select coordinator.coordinator_id, coordinator.name,
            student.student_id AS student_id, student.name AS student_name
            from student INNER JOIN coordinator ON student.coordinator_id = coordinator.coordinator_id
            WHERE coordinator.coordinator_id = (?)
            """),
    DELETE_COORDINATOR_BY_ID("""
             DELETE from coordinator where coordinator_id=(?);
            """),
    GET_ALL_COORDINATORS("""
            SELECT coordinator_id, name FROM coordinator
            """),
    UPDATE_COORDINATOR_NAME_BY_ID("""
            UPDATE coordinator set name=(?) where coordinator_id=(?);
            """);

    final String QUERY;

}
