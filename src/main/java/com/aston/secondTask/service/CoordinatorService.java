package com.aston.secondTask.service;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CoordinatorService {

    private final CoordinatorDAO coordinatorDAO;
    public int createCoordinator(CoordinatorDTO coordinator) throws SQLException {
        CoordinatorEntity coordinatorEntity = CoordinatorEntity.builder().name(coordinator.getName()).build();
     return   coordinatorDAO.createCoordinator(coordinatorEntity);

    }

    public int deleteById(int coordinatorId) throws SQLException {
        return coordinatorDAO.deleteById(coordinatorId);
    }

    public int updateCoordinatorName(int coordinatorId, String name) throws SQLException {
        return coordinatorDAO.updateCoordinatorName(coordinatorId, name);
    }

    public CoordinatorDTO findById(int coordinatorId) throws SQLException {
      CoordinatorEntity coordinatorEntity = coordinatorDAO.findCoordinatorWithStudentsByID(coordinatorId);
       return getCoordinatorDTOWithStudents(coordinatorEntity);

    }

    public Set<CoordinatorDTO> findAll() throws SQLException {
        List<CoordinatorEntity> allCoordinatorEntities = coordinatorDAO.findAll();
       return allCoordinatorEntities.stream()
                .map(this::getCoordinatorDTO)
                        .collect(Collectors.toSet());

    }

    private CoordinatorDTO getCoordinatorDTO(CoordinatorEntity coordinatorEntity) {
        return CoordinatorDTO.builder()
                .id(coordinatorEntity.getId())
                .name(coordinatorEntity.getName())
                .build();
    }

    private CoordinatorDTO getCoordinatorDTOWithStudents(CoordinatorEntity coordinatorEntity) {
        return CoordinatorDTO.builder()
                .id(coordinatorEntity.getId())
                .name(coordinatorEntity.getName())
                .students(coordinatorEntity.getStudents().stream()
                        .map(entity -> StudentDTO.builder().id(entity.getId())
                                .name(entity.getName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
