package com.aston.secondTask.infrastructure.security.securityRepositories;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.StudentEntity;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
@ToString(of = {"userId", "userName", "email", "password"})
public class UserEntity {

    private int userId;
    private String userName;
    private String email;
    private String password;
    private StudentEntity Student;
    private CoordinatorEntity Coordinator;

}
