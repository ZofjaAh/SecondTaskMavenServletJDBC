package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    int deleteCourse(int courseId);

    List<CourseEntity> findAll();

    Optional<CourseEntity> findById(int courseId);

    int createCourse(CourseEntity course);

    int updateCourseName(int courseId, String name);
}
