package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.CoordinatorEntity;

import java.util.List;

public interface CoordinatorDAO {
    int deleteById(int coordinatorId);

    List<CoordinatorEntity> findAll();

    int createCoordinator(CoordinatorEntity coordinator);

    int updateCoordinatorName(int coordinatorId, String name);

    CoordinatorEntity findCoordinatorWithStudentsByID(int coordinatorId);
}
