package com.aston.secondTask.service;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
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
       return allCourses.stream().map(this::getCourseDTO)
                .collect(Collectors.toSet());
    }

    public int deleteCourse(int courseId) throws SQLException {
        return courseDAO.deleteCourse(courseId);
    }

    public int updateCourseName(int courseId, String name) throws SQLException {
        return courseDAO.updateCourseName(courseId, name);
    }

    private CourseDTO getCourseDTO(CourseEntity entity) {
        return  CourseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
