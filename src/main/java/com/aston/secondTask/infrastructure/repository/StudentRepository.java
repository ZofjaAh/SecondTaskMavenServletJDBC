package com.aston.secondTask.infrastructure.repository;

import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.infrastructure.configuration.SessionManager;
import com.aston.secondTask.service.DAO.StudentDAO;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Setter
public class StudentRepository implements StudentDAO {

    private final SessionManager sessionManager;

    @Override
    public int deleteStudent(int studentId) {
        return 0;
    }

    @Override
    public List<StudentEntity> findAll() {
        return null;
    }

    @Override
    public Optional<StudentEntity> findById(int studentId) {
        return Optional.empty();
    }

    @Override
    public int createStudentWithCoordinatorAndCourse(StudentEntity student) {
        return 0;
    }

    @Override
    public int updateCoordinatorName(int studentId, String name) {
        return 0;
    }
}
