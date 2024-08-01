package com.aston.secondTask.service;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class CoordinatorService {
    private final CoordinatorDAO coordinatorDAO;
    public int createCoordinator(CoordinatorDTO coordinator) throws SQLException {
        CoordinatorEntity coordinatorEntity = CoordinatorEntity.builder().name(coordinator.getName()).build();
     return   coordinatorDAO.createCoordinator(coordinatorEntity);

    }

    public int deleteById(int coordinatorId) {
        return 0;

    }

    public int updateCoordinatorName(int coordinatorId, String name) {
        return 0;
    }

    public Optional<CoordinatorDTO> findById(int coordinatorId) {
        return null;
    }

    public Set<CoordinatorDTO> findAll() {
        return null;
    }
}
