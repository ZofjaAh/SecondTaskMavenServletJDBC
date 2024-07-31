package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    int deleteStudent(int studentId);

    List<StudentEntity> findAll();

    Optional<StudentEntity> findById(int studentId);

    int createStudentWithCoordinatorAndCourse(StudentEntity student);

    int updateCoordinatorName(int studentId, String name);
}
