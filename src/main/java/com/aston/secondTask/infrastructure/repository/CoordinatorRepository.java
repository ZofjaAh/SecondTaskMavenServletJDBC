package com.aston.secondTask.infrastructure.repository;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.infrastructure.configuration.SessionManager;
import com.aston.secondTask.infrastructure.repository.mapper.ResultSetMapper;
import com.aston.secondTask.infrastructure.repository.queries.CoordinatorSQLQuery;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Setter
public class CoordinatorRepository implements CoordinatorDAO {
    private final SessionManager sessionManager;
    private final ResultSetMapper resultSetMapper;


    @Override
    public List<CoordinatorEntity> findAll() throws SQLException {
            List<CoordinatorEntity> coordinatorEntityList = new ArrayList<>();
            sessionManager.beginSession();
            try (Connection connection = sessionManager.getCurrentSession();
                 PreparedStatement statement = connection.prepareStatement(
                         CoordinatorSQLQuery.GET_ALL_COORDINATORS.getQUERY())) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        CoordinatorEntity coordinator = resultSetMapper.parseCoordinatorFromResultSet(result);
                        coordinatorEntityList.add(coordinator);
                    }
                }
            } catch (SQLException e) {
                log.error("SQLException with loading all coordinators [{}]", e.getMessage());;
                sessionManager.rollbackSession();
                throw e;
            }
            return  coordinatorEntityList;
    }

    @Override
    public int createCoordinator(CoordinatorEntity coordinator) throws SQLException {
        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(
                     CoordinatorSQLQuery.CREATE_COORDINATOR.getQUERY(),
                     Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, coordinator.getName());
            statement.executeUpdate();
            try (ResultSet result = statement.getGeneratedKeys()) {
                result.next();
                int id = result.getInt(1);
                sessionManager.commitSession();
                return id;
            }
        } catch (SQLException e) {
            log.error("SQLException with creation coordinator with name: [{}] - [{}]",
                    coordinator.getName(), e.getMessage());
            sessionManager.rollbackSession();
            throw e;
        }
    }


    @Override
    public int deleteById(int coordinatorId) throws SQLException {
        int updated_rows;

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(
                     CoordinatorSQLQuery.DELETE_COORDINATOR_BY_ID.getQUERY())) {
            statement.setLong(1, coordinatorId);
            updated_rows = statement.executeUpdate();
            sessionManager.commitSession();

        } catch (SQLException e) {
            log.error("SQLException with deleting coordinator with Id: [{}] - [{}]",
                   coordinatorId, e.getMessage());;
            sessionManager.rollbackSession();
            throw e;
        }
        return updated_rows;
    }


    @Override
    public int updateCoordinatorName(int coordinatorId, String coordinatorName) throws SQLException {
        int rowsUpdated = 0;
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(
                    CoordinatorSQLQuery.UPDATE_COORDINATOR_NAME_BY_ID.getQUERY())) {
            statement.setString(1, coordinatorName);
            statement.setInt(2, coordinatorId);
            rowsUpdated = statement.executeUpdate();
            sessionManager.commitSession();

        } catch (SQLException e) {
            log.error("SQLException with changing coordinator's name with Id: [{}] - [{}]",
                    coordinatorId, e.getMessage());
            throw e;
        }
        return rowsUpdated;
    }
    @Override
    public Optional<CoordinatorEntity> findCoordinatorWithStudentsByID(int coordinatorId) throws SQLException {
      List<CoordinatorEntity> coordinatorEntityList = new ArrayList<>();
      CoordinatorEntity coordinatorEntity = null;
        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(
                     CoordinatorSQLQuery.GET_COORDINATOR_WITH_STUDENTS_BY_ID.getQUERY())) {
            statement.setInt(1, coordinatorId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    CoordinatorEntity coordinator = resultSetMapper.parseCoordinatorFromResultSet(result);
                   coordinatorEntityList.add(coordinator);
                }
            }
            coordinatorEntity = createCoordinatorEntityFromListCoordinators(coordinatorEntityList);
        } catch (SQLException e) {
            log.error("SQLException with loading coordinator by Id: [{}] - [{}]",coordinatorId, e.getMessage());;
            sessionManager.rollbackSession();
            throw e;
        }

        return Optional.ofNullable(coordinatorEntity);
    }

    private CoordinatorEntity createCoordinatorEntityFromListCoordinators(List<CoordinatorEntity> coordinators) {
        Set<StudentEntity> studentEntitySet = coordinators.stream()
                .flatMap((CoordinatorEntity coordinator) -> coordinator.getStudents().stream())
                .collect(Collectors.toSet());
        return coordinators.get(0).withStudents(studentEntitySet);
    }
}
