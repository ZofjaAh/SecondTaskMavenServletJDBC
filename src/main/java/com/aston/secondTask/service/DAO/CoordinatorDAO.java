package com.aston.secondTask.service.DAO;

import com.aston.secondTask.entities.CoordinatorEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CoordinatorDAO {
    int deleteById(int coordinatorId) throws SQLException;

    List<CoordinatorEntity> findAll() throws SQLException;

    int createCoordinator(CoordinatorEntity coordinator) throws SQLException;

    int updateCoordinatorName(int coordinatorId, String name) throws SQLException;

    CoordinatorEntity findCoordinatorWithStudentsByID(int coordinatorId) throws SQLException;
}
