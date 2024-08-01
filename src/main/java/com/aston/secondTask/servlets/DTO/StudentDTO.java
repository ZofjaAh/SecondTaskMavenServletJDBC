package com.aston.secondTask.servlets.DTO;

import com.aston.secondTask.entities.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private int id;
    private String name;
    private String coordinatorName;
    private Set<CourseDTO> courses;

}
