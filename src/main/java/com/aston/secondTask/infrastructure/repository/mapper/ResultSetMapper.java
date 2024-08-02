package com.aston.secondTask.infrastructure.repository.mapper;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.entities.StudentEntity;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class ResultSetMapper {
    public CoordinatorEntity parseCoordinatorFromResultSet(ResultSet result) throws SQLException {
        CoordinatorEntity coordinator = new CoordinatorEntity();
        coordinator.setId(Integer.parseInt(result.getString("id")));
        coordinator.setName(result.getString("name"));
        String studentId = result.getString("student_id");
        if (studentId != null) {
            StudentEntity student = new StudentEntity();
            student.setId(Integer.parseInt(studentId));
            student.setName(result.getString("student_name"));
            Set<StudentEntity> studentEntitySet = new HashSet<>();
            studentEntitySet.add(student);
            coordinator.setStudents(new HashSet<>(studentEntitySet));
        }
        return coordinator;
    }

    public StudentEntity parseStudentFromResultSet(ResultSet result) throws SQLException {
        StudentEntity student = new StudentEntity();
        student.setId(Integer.parseInt(result.getString("id")));
        student.setName(result.getString("name"));
        String coordinatorId = result.getString("coordinator_id");
        String courseId = result.getString("curse_id");
        if (coordinatorId != null) {
            CoordinatorEntity coordinator = new CoordinatorEntity();
            coordinator.setId(Integer.parseInt(coordinatorId));
            student.setCoordinator(coordinator);
        }
        if (courseId != null){
            Set<CourseEntity> courseEntitySet = new HashSet<>();
            CourseEntity course = new CourseEntity();
            course.setId(Integer.parseInt(courseId));
            course.setName(result.getString("name"));
            courseEntitySet.add(course);
            student.setCourses(new HashSet<>(courseEntitySet));
        }

        return student;
    }

    public CourseEntity parseCourseFromResultSet(ResultSet result) throws SQLException {
        CourseEntity course = new CourseEntity();
        course.setId(Integer.parseInt(result.getString("id")));
        course.setName(result.getString("name"));
        return course;
    }
}
