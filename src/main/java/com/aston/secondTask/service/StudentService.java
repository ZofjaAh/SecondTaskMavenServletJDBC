package com.aston.secondTask.service;

import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

/**
 * Service for handling student-related operations.
 */
@AllArgsConstructor
public class StudentService {
    private final StudentDAO studentDAO;

    /**
     * Finds a student by ID along with their courses.
     *
     * @param studentId the ID of the student
     * @return the StudentDTO containing student and course information
     */

    public StudentDTO findStudentWithCoursesByID(int studentId) {
        StudentEntity studentEntity = studentDAO.findById(studentId);
        return StudentDTO.builder()
                .id(studentEntity.getId())
                .name(studentEntity.getName())
                .coordinatorId(studentEntity.getCoordinator() != null ?
                        studentEntity.getCoordinator().getId() : null)
                .courses(studentEntity.getCourses().stream()
                        .map(this::getCourseDTO)
                        .collect(Collectors.toSet()))
                .build();


    }

    /**
     * Creates a student with a specified coordinator.
     *
     * @param student       the student data
     * @param coordinatorId the ID of the coordinator
     * @return the generated ID of the new student
     */

    public int createStudentWithCoordinator(StudentDTO student, int coordinatorId) {
        StudentEntity studentEntity = StudentEntity.builder()
                .name(student.getName()).build();
        return studentDAO.createStudentWithCoordinator(studentEntity, coordinatorId);

    }

    /**
     * Deletes a student by ID.
     *
     * @param studentId the ID of the student
     * @return the number of rows affected
     */
    public int deleteStudent(int studentId) {
        return studentDAO.deleteStudent(studentId);
    }

    /**
     * Updates the coordinator for a student.
     *
     * @param studentId     the ID of the student
     * @param coordinatorId the ID of the coordinator
     * @return the number of rows affected
     */
    public int updateCoordinator(int studentId, int coordinatorId) {
        return studentDAO.updateCoordinator(studentId, coordinatorId);
    }

    /**
     * Adds a course to a student.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @return the number of rows affected
     */
    public int addStudentCourse(int studentId, int courseId) {
        return studentDAO.addCourse(studentId, courseId);
    }

    /**
     * Converts a CourseEntity to a CourseDTO.
     *
     * @param course the course entity
     * @return the CourseDTO
     */
    private StudentDTO.CourseDTO getCourseDTO(CourseEntity course) {
        return StudentDTO.CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }
}
