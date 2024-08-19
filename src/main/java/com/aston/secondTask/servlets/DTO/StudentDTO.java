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
    private Integer coordinatorId;
    private Set<CourseDTO> courses;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseDTO {
        private int id;
        private String name;
    }
}
