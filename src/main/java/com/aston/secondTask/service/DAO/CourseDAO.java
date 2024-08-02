package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.CourseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseDAO {
    int deleteCourse(int courseId) throws SQLException;

    Set<CourseEntity> findAll() throws SQLException;

    int createCourse(CourseEntity course) throws SQLException;

    int updateCourseName(int courseId, String name) throws SQLException;
}
