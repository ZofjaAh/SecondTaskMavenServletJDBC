package com.aston.secondTask.service;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.aston.secondTask.service.exeptions.NotFoundException;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import lombok.AllArgsConstructor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class StudentService {
    private  final StudentDAO studentDAO;

    public  StudentDTO findStudentWithCoursesByID(int studentId) {
        Optional<StudentEntity> studentOptional = studentDAO.findById(studentId);
        if(studentOptional.isPresent()) {
            StudentEntity studentEntity = studentOptional.get();
            return StudentDTO.builder()
                    .id(studentEntity.getId())
                    .name(studentEntity.getName())
                    .courses(studentEntity.getCourses().stream()
                            .map(course -> CourseDTO.builder().id(course.getId())
                                    .name(course.getName()).build()).collect(Collectors.toSet()))
                            .build();
        } else throw new NotFoundException("Student with such Id not exist");

    }

       public int createStudentWithCoordinator(StudentDTO student, String coordinatorName) {
        StudentEntity studentEntity = StudentEntity.builder()
                .name(student.getName()).build();
      return   studentDAO.createStudentWithCoordinator(studentEntity, coordinatorName);

    }

    public int deleteStudent(int studentId) {
        return 0;
    }

    public int updateCoordinatorName(int studentId, String name) {
        return 0;
    }

    public Optional<StudentDTO> findById(int studentId) {
        return null;
    }

    public Set<StudentDTO> findAll() {
        return null;
    }
}
