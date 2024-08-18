package com.aston.secondTask.service;

import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Service for handling course-related operations.
 */
@AllArgsConstructor
public class CourseService {
    private final CourseDAO courseDAO;

    /**
     * Creates a new course.
     *
     * @param course the course data
     * @return the generated ID of the new course
     */
    public int createCourse(CourseDTO course)  {
        CourseEntity courseEntity = CourseEntity.builder().name(course.getName()).build();
        return courseDAO.createCourse(courseEntity);

    }

    /**
     * Finds all courses.
     *
     * @return a set of CourseDTO containing all courses
     */

    public Set<CourseDTO> findAll() {
        Set<CourseEntity> allCourses = courseDAO.findAll();
        return allCourses.stream().map(this::getCourseDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Deletes a course by ID.
     *
     * @param courseId the ID of the course
     * @return the number of rows affected
     */

    public int deleteCourse(int courseId)  {
        return courseDAO.deleteCourse(courseId);
    }

    /**
     * Updates the name of a course.
     *
     * @param courseId the ID of the course
     * @param name the new name of the course
     * @return the number of rows affected
     */

    public int updateCourseName(int courseId, String name)  {

        return courseDAO.updateCourseName(courseId, name);
    }

    /**
     * Converts a CourseEntity to a CourseDTO.
     *
     * @param entity the course entity
     * @return the CourseDTO
     */
    private CourseDTO getCourseDTO(CourseEntity entity) {
        return CourseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
