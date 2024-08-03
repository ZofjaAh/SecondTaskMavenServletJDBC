package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.StudentEntity;

import java.sql.SQLException;

public interface StudentDAO {
    int createStudentWithCoordinator(StudentEntity studentEntity, int coordinatorId) throws SQLException;

    int deleteStudent(int studentId) throws SQLException;

    int updateCoordinator(int studentId, int coordinatorId) throws SQLException;

    int addCourse(int studentId, int courseId) throws SQLException;

    StudentEntity findById(int studentId) throws SQLException;


}
