package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.CourseEntity;

import java.sql.SQLException;
import java.util.Set;

public interface CourseDAO {
    int deleteCourse(int courseId) ;

    Set<CourseEntity> findAll() ;

    int createCourse(CourseEntity course) ;

    int updateCourseName(int courseId, String name) ;
}
