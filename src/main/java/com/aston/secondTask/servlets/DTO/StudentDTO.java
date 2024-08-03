package com.aston.secondTask.servlets.DTO;

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
    private int coordinatorId;
    private String coordinatorName;
    private Set<CourseDTO> courses;

}
