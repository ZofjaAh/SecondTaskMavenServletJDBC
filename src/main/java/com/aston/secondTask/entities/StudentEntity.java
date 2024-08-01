package com.aston.secondTask.entities;

import com.aston.secondTask.infrastructure.security.securityRepositories.UserEntity;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name"})
public class StudentEntity {
    private int id;
    private String name;
    private CoordinatorEntity coordinator;
    private Set<CourseEntity> courses;


}
