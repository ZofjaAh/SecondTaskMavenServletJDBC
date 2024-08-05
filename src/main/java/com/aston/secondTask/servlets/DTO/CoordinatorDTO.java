package com.aston.secondTask.servlets.DTO;

import lombok.*;

import java.util.Comparator;
import java.util.Set;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatorDTO  {
    private int id;
    private String name;
    private Set<StudentDTO> students;



}
