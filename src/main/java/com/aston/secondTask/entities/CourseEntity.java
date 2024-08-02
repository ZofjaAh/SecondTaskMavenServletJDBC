package com.aston.secondTask.entities;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class CourseEntity {
    private int id;
    private String name;
    private Set<StudentEntity> students;

}
