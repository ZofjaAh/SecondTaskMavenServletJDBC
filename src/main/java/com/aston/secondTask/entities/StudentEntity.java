package com.aston.secondTask.entities;

import lombok.*;

import java.util.Set;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class StudentEntity {
    private int id;
    private String name;
    private CoordinatorEntity coordinator;
    private Set<CourseEntity> courses;


}
