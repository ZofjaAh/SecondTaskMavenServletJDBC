package com.aston.secondTask.service;

import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CourseService {
private final CourseDAO courseDAO;
    public int createCourse(CourseDTO course) throws SQLException {
        CourseEntity courseEntity = CourseEntity.builder().name(course.getName()).build();
       return courseDAO.createCourse(courseEntity);

    }

    public Set<CourseDTO> findAll() throws SQLException {
        Set<CourseEntity> allCourses = courseDAO.findAll();
        Set<CourseDTO> courses = allCourses.stream().map(entity -> new CourseDTO()
                        .builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .build())
                .collect(Collectors.toSet());
        return courses;
    }

    public int deleteCourse(int courseId) {
        return 0;
    }

    public int updateCourseName(int courseId, String name) {
        return 0;
    }

    public Optional<CourseDTO> findById(int courseId) {
        return null;
    }
}
