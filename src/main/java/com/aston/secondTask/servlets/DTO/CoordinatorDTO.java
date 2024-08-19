package com.aston.secondTask.servlets.DTO;

import lombok.*;

import java.util.Set;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatorDTO {
    private int id;
    private String name;
    private Set<StudentDTO> students;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentDTO {
        private int id;
        private String name;
    }


}
