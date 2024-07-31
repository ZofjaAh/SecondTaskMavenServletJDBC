package com.aston.secondTask.entities;

import com.aston.secondTask.infrastructure.security.securityRepositories.UserEntity;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name"})
public class CoordinatorEntity {
    private int id;
    private String name;
    private Set<StudentEntity> students;
    private UserEntity user;

}
