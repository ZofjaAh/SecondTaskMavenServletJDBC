package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.CoordinatorEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CoordinatorDAO {
    int deleteById(int coordinatorId);

    List<CoordinatorEntity> findAll();

    Optional<CoordinatorEntity> findById(int coordinatorId);

    int createCoordinator(CoordinatorEntity coordinator) throws SQLException;

    int updateCoordinatorName(int coordinatorId, String name);
}
