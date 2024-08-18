package com.aston.secondTask.service;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Service for handling coordinator-related operations.
 */

@AllArgsConstructor
public class CoordinatorService {

    private final CoordinatorDAO coordinatorDAO;
    /**
     * Creates a new coordinator.
     *
     * @param coordinator the coordinator data
     * @return the generated ID of the new coordinator
     */
    public int createCoordinator(CoordinatorDTO coordinator) {
        CoordinatorEntity coordinatorEntity = CoordinatorEntity.builder()
                .name(coordinator.getName())
                .build();
        return coordinatorDAO.createCoordinator(coordinatorEntity);

    }
    /**
     * Deletes a coordinator by ID.
     *
     * @param coordinatorId the ID of the coordinator
     * @return the number of rows affected
     */

    public int deleteById(int coordinatorId)  {
        return coordinatorDAO.deleteById(coordinatorId);
    }

    /**
     * Updates the name of a coordinator.
     *
     * @param coordinatorId the ID of the coordinator
     * @param name the new name of the coordinator
     * @return the number of rows affected
     */
    public int updateCoordinatorName(int coordinatorId, String name){
        return coordinatorDAO.updateCoordinatorName(coordinatorId, name);
    }
    /**
     * Finds a coordinator by ID along with their students.
     *
     * @param coordinatorId the ID of the coordinator
     * @return the CoordinatorDTO containing coordinator and student information
     */
    public CoordinatorDTO findById(int coordinatorId) {
        CoordinatorEntity coordinatorEntity = coordinatorDAO.findCoordinatorWithStudentsByID(coordinatorId);
        return getCoordinatorDTOWithStudents(coordinatorEntity);

    }
    /**
     * Finds all coordinators.
     *
     * @return a set of CoordinatorDTO containing all coordinators
     */

    public Set<CoordinatorDTO> findAll() {
        List<CoordinatorEntity> allCoordinatorEntities = coordinatorDAO.findAll();
        return allCoordinatorEntities.stream()
                .map(this::getCoordinatorDTO)
                .collect(Collectors.toSet());

    }
    /**
     * Converts a CoordinatorEntity to a CoordinatorDTO.
     *
     * @param coordinatorEntity the coordinator entity
     * @return the CoordinatorDTO
     */

    private CoordinatorDTO getCoordinatorDTO(CoordinatorEntity coordinatorEntity) {
        return CoordinatorDTO.builder()
                .id(coordinatorEntity.getId())
                .name(coordinatorEntity.getName())
                .build();
    }
    /**
     * Converts a CoordinatorEntity to a CoordinatorDTO with students.
     *
     * @param coordinatorEntity the coordinator entity
     * @return the CoordinatorDTO with students
     */

    private CoordinatorDTO getCoordinatorDTOWithStudents(CoordinatorEntity coordinatorEntity) {
        return CoordinatorDTO.builder()
                .id(coordinatorEntity.getId())
                .name(coordinatorEntity.getName())
                .students(coordinatorEntity.getStudents().stream()
                        .map(entity -> CoordinatorDTO.StudentDTO.builder().id(entity.getId())
                                .name(entity.getName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
