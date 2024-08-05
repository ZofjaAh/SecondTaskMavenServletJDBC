package com.aston.secondTask.servlets.DTO;

import lombok.*;

import java.util.Set;


@Data
@With
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
