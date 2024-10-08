package com.aston.secondTask.entities;

import lombok.*;

import java.util.Set;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name"})
public class CoordinatorEntity {
    private int id;
    private String name;
    private Set<StudentEntity> students;
}
