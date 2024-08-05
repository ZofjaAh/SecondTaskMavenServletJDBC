package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.StudentEntity;

import java.sql.SQLException;

public interface StudentDAO {
    int createStudentWithCoordinator(StudentEntity studentEntity, int coordinatorId) ;

    int deleteStudent(int studentId) ;

    int updateCoordinator(int studentId, int coordinatorId) ;

    int addCourse(int studentId, int courseId) ;

    StudentEntity findById(int studentId) ;


}
