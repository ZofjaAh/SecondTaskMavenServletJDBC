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
public class CoordinatorDTO {
    private int id;
    private String name;
    private Set<StudentDTO> students;

}
