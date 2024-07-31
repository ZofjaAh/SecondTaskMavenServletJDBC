package com.aston.secondTask.infrastructure.repository;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.infrastructure.configuration.SessionManager;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Setter
public class CoordinatorRepository implements CoordinatorDAO {
    private final SessionManager sessionManager;

    @Override
    public int deleteById(int coordinatorId) {
        return 0;
    }

    @Override
    public List<CoordinatorEntity> findAll() {
        return null;
    }

    @Override
    public Optional<CoordinatorEntity> findById(int coordinatorId) {
        return Optional.empty();
    }

    @Override
    public int createCoordinator(CoordinatorEntity coordinator) {
        return 0;
    }

    @Override
    public int updateCoordinatorName(int coordinatorId, String name) {
        return 0;
    }
}
