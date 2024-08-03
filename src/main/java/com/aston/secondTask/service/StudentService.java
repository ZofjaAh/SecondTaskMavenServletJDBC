package com.aston.secondTask.service;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.aston.secondTask.service.exeptions.NotFoundException;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import lombok.AllArgsConstructor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class StudentService {
    private  final StudentDAO studentDAO;

    public  StudentDTO findStudentWithCoursesByID(int studentId) throws SQLException {
        StudentEntity studentEntity = studentDAO.findById(studentId);
        return StudentDTO.builder()
                    .id(studentEntity.getId())
                    .name(studentEntity.getName())
                    .courses(studentEntity.getCourses().stream()
                            .map(this::getCourseDTO)
                            .collect(Collectors.toSet()))
                            .build();


    }

    public int createStudentWithCoordinator(StudentDTO student, int coordinatorId) throws SQLException {
        StudentEntity studentEntity = StudentEntity.builder()
                .name(student.getName()).build();
      return studentDAO.createStudentWithCoordinator(studentEntity, coordinatorId);

    }

    public int deleteStudent(int studentId) throws SQLException {
      return  studentDAO.deleteStudent(studentId);
    }

    public int updateCoordinatorName(int studentId, int coordinatorId) throws SQLException {
        return studentDAO.updateCoordinator(studentId,coordinatorId);
    }
    public int addStudentCourse ( int studentId, int courseId) throws SQLException {
        return studentDAO.addCourse(studentId, courseId);
    }

    private CourseDTO getCourseDTO(CourseEntity course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }
}
