package com.aston.secondTask.servlets.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CourseDTO {
    private int id;
    private String name;
    private Set<StudentDTO> students;

}
