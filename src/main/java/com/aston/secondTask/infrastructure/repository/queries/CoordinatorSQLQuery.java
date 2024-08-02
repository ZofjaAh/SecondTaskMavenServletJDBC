package com.aston.secondTask.infrastructure.repository.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoordinatorSQLQuery {
    CREATE_COORDINATOR("""
            INSERT INTO coordinator (name) 
            VALUES (?) RETURNING id;              
             """),
    GET_COORDINATOR_WITH_STUDENTS_BY_ID("""
            select coordinator.id, coordinator.name,
            student.id AS student_id, student.name AS student_name
            from student INNER JOIN coordinator ON student.coordinator_id = coordinator.id
            WHERE coordinator.id = (?)
            """),
    DELETE_COORDINATOR_BY_ID("""
             DELETE from coordinator where id=(?);
            """),
    GET_ALL_COORDINATORS("""
            SELECT id, name FROM coordinator
            """),
    UPDATE_COORDINATOR_NAME_BY_ID("""
            UPDATE coordinator set name=(?) where id=(?);
            """);

    final String QUERY;

}
