package com.aston.secondTask.infrastructure.repository;

import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.infrastructure.configuration.SessionManager;
import com.aston.secondTask.service.DAO.CourseDAO;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Setter
public class CourseRepository implements CourseDAO {

    private final SessionManager sessionManager;

    @Override
    public int deleteCourse(int courseId) {
        return 0;
    }

    @Override
    public List<CourseEntity> findAll() {
        return null;
    }

    @Override
    public Optional<CourseEntity> findById(int courseId) {
        return Optional.empty();
    }

    @Override
    public int createCourse(CourseEntity course) {
        return 0;
    }

    @Override
    public int updateCourseName(int courseId, String name) {
        return 0;
    }
}
