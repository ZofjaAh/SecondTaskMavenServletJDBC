package com.aston.secondTask.entities;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name"})
public class CourseEntity {
    private int id;
    private String name;
    private Set<StudentEntity> students;

}
