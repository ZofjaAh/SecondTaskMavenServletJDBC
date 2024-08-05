package com.aston.secondTask.service;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DTOFixtures;
import util.EntityFixtures;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @InjectMocks
    private CourseService courseService;
    @Mock
    private CourseDAO courseDAO;
    @Test
    void shouldCreateCourseCorrectly(){
       CourseDTO courseDTO =  DTOFixtures.course_1();
        CourseEntity courseEntity = EntityFixtures.course_1();
        int courseId = 1;
        when(courseDAO.createCourse(courseEntity)).thenReturn(courseId);
        int result = courseService.createCourse(courseDTO);
        Assertions.assertEquals(courseId, result);
    }
    @Test
    void shouldDeleteCourseSuccessful(){
        int courseId = 1;
        int numberChangedRows = 2;
        when(courseDAO.deleteCourse(courseId)).thenReturn(numberChangedRows);
        int result = courseService.deleteCourse(courseId);
        Assertions.assertEquals(numberChangedRows, result);
    }
    @Test
    void shouldUpdateCourseNameSuccessful(){
        int courseId = 1;
        int numberChangedRows = 1;
       String courseName = "Play Chess";
        when(courseDAO.updateCourseName(courseId, courseName)).thenReturn(numberChangedRows);
        int result = courseService.updateCourseName(courseId, courseName);
        Assertions.assertEquals(numberChangedRows, result);
    }
    @Test
    void shouldSearchAllCoursesSuccessful(){
        Set<CourseEntity> courseEntityList = Set.of(EntityFixtures.course_1_0(), EntityFixtures.course_2_0());
        Set<CourseDTO> courseDTOSet = Set.of(DTOFixtures.course_1_0(), DTOFixtures.course_2_0());
        when(courseDAO.findAll()).thenReturn(courseEntityList);
        Set<CourseDTO> result = courseService.findAll();
        List<String> courseNames = courseDTOSet.stream().map(CourseDTO::getName).sorted().toList();
        List<String> resultNames = result.stream().map(CourseDTO::getName).sorted().toList();
        Assertions.assertEquals(courseDTOSet.size(), result.size());
        Assertions.assertEquals(courseNames,resultNames);
    }



}