package com.aston.secondTask.service;

import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CourseService {
    private final CourseDAO courseDAO;

    public int createCourse(CourseDTO course)  {
        CourseEntity courseEntity = CourseEntity.builder().name(course.getName()).build();
        return courseDAO.createCourse(courseEntity);

    }

    public Set<CourseDTO> findAll() {
        Set<CourseEntity> allCourses = courseDAO.findAll();
        return allCourses.stream().map(this::getCourseDTO)
                .collect(Collectors.toSet());
    }

    public int deleteCourse(int courseId)  {
        return courseDAO.deleteCourse(courseId);
    }

    public int updateCourseName(int courseId, String name)  {
        return courseDAO.updateCourseName(courseId, name);
    }

    private CourseDTO getCourseDTO(CourseEntity entity) {
        return CourseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
