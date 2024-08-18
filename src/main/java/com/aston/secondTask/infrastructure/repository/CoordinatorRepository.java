package com.aston.secondTask.infrastructure.repository;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.infrastructure.configuration.DateBaseConnectionCreator;
import com.aston.secondTask.infrastructure.repository.queries.CoordinatorSQLQuery;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.exeptions.NotFoundException;
import com.aston.secondTask.service.exeptions.ProcessingException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Repository for handling coordinator-related database operations.
 */
@AllArgsConstructor
@Slf4j
@Setter
public class CoordinatorRepository  implements CoordinatorDAO {


DateBaseConnectionCreator dateBaseConnectionCreator;

    /**
     * Finds all coordinators.
     *
     * @return a list of CoordinatorEntity containing all coordinators
     */
    @Override
    public List<CoordinatorEntity> findAll() {
        List<CoordinatorEntity> coordinatorEntityList = new ArrayList<>();
        try (Connection connection = dateBaseConnectionCreator.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                     CoordinatorSQLQuery.GET_ALL_COORDINATORS.getQUERY())) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    CoordinatorEntity coordinator = parseCoordinatorWithoutStudentsFromResultSet(result);
                    coordinatorEntityList.add(coordinator);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException with loading all coordinators [{}]", e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
        return coordinatorEntityList;
    }
    /**
     * Creates a new coordinator.
     *
     * @param coordinator the coordinator entity
     * @return the generated ID of the new coordinator
     */
    @Override
    public int createCoordinator(CoordinatorEntity coordinator)  {
        try (Connection connection = dateBaseConnectionCreator.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     CoordinatorSQLQuery.CREATE_COORDINATOR.getQUERY(),
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, coordinator.getName());
            statement.executeUpdate();
            try (ResultSet result = statement.getGeneratedKeys()) {
                result.next();
                int id = result.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            log.error("SQLException with creation coordinator with name: [{}] - [{}]",
                    coordinator.getName(), e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
    }

    /**
     * Deletes a coordinator by ID.
     *
     * @param coordinatorId the ID of the coordinator
     * @return the number of rows affected
     */
    @Override
    public int deleteById(int coordinatorId){
        int updated_rows = 0;
        try { Connection connection = dateBaseConnectionCreator.getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement changeStudentsStatement = connection.prepareStatement(
                     CoordinatorSQLQuery.CHANGE_STUDENT_COORDINATOR_ID_BY_ID.getQUERY());
             PreparedStatement deleteCoordinatorStatement = connection.prepareStatement(
                     CoordinatorSQLQuery.DELETE_COORDINATOR_BY_ID.getQUERY())) {
            changeStudentsStatement.setLong(1, coordinatorId);
            deleteCoordinatorStatement.setLong(1, coordinatorId);
            updated_rows += changeStudentsStatement.executeUpdate();
            updated_rows += deleteCoordinatorStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("SQLException with deleting coordinator with Id: [{}] - [{}]",
                    coordinatorId, e.getMessage());

            throw new ProcessingException(e.getMessage());
        }

            connection.commit();
        } catch (SQLException e) {
            log.error("SQLException with closing connection [{}]", e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
        return updated_rows;
    }

    /**
     * Updates the name of a coordinator.
     *
     * @param coordinatorId the ID of the coordinator
     * @param coordinatorName the new name of the coordinator
     * @return the number of rows affected
     */

    @Override
    public int updateCoordinatorName(int coordinatorId, String coordinatorName)  {
        int rowsUpdated = 0;
        try (Connection connection = dateBaseConnectionCreator.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     CoordinatorSQLQuery.UPDATE_COORDINATOR_NAME_BY_ID.getQUERY())) {
            statement.setString(1, coordinatorName);
            statement.setInt(2, coordinatorId);
            rowsUpdated = statement.executeUpdate();

        } catch (SQLException e) {
            log.error("SQLException with changing coordinator's name with Id: [{}] - [{}]",
                    coordinatorId, e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
        return rowsUpdated;
    }

    /**
     * Finds a coordinator by ID along with their students.
     *
     * @param coordinatorId the ID of the coordinator
     * @return the CoordinatorEntity containing coordinator and student information
     * @throws ProcessingException if an error occurs during processing
     * @throws NotFoundException if the coordinator with the specified ID does not exist
     */
    @Override
    public CoordinatorEntity findCoordinatorWithStudentsByID(int coordinatorId) {
        List<CoordinatorEntity> coordinatorEntityList = new ArrayList<>();
        CoordinatorEntity coordinatorEntity = null;
        try (Connection connection = dateBaseConnectionCreator.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     CoordinatorSQLQuery.GET_COORDINATOR_WITH_STUDENTS_BY_ID.getQUERY())) {
            statement.setInt(1, coordinatorId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    CoordinatorEntity coordinator = parseCoordinatorFromResultSet(result);
                    coordinatorEntityList.add(coordinator);
                }
            }
            coordinatorEntity = createCoordinatorEntityFromListCoordinators(coordinatorEntityList);
        } catch (SQLException | IndexOutOfBoundsException e) {
            log.error("SQLException with loading coordinator by Id: [{}] - [{}]", coordinatorId, e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
        if (Objects.nonNull(coordinatorEntity)) {
            return coordinatorEntity;
        } else {
            log.error("Coordinator with such Id: [{}] doesn't exist", coordinatorId);
            throw new NotFoundException("Sorry, coordinator with such Id: [{}] doesn't exist");
        }
    }

    /**
     * Creates a CoordinatorEntity from a list of coordinators.
     *
     * @param coordinators the list of CoordinatorEntity
     * @return the CoordinatorEntity with students
     */
    private CoordinatorEntity createCoordinatorEntityFromListCoordinators(List<CoordinatorEntity> coordinators) {
        Set<StudentEntity> studentEntitySet = coordinators.stream()
                .filter(coordinator-> Objects.nonNull(coordinator.getStudents()))
                .flatMap((CoordinatorEntity coordinator) -> coordinator.getStudents().stream())
                .collect(Collectors.toSet());
        return coordinators.get(0).withStudents(studentEntitySet);
    }

    /**
     * Parses a CoordinatorEntity from a ResultSet.
     *
     * @param result the ResultSet containing coordinator data
     * @return the CoordinatorEntity
     * @throws SQLException if an error occurs while accessing the ResultSet
     */
    public CoordinatorEntity parseCoordinatorFromResultSet(ResultSet result) throws SQLException {

        CoordinatorEntity coordinator = new CoordinatorEntity();
        coordinator.setId(Integer.parseInt(result.getString("coordinator_id")));
        coordinator.setName(result.getString("name"));
        String studentId = result.getString("student_id");
        if(studentId != null) {
            StudentEntity student = new StudentEntity();
            student.setId(Integer.parseInt(studentId));
            student.setName(result.getString("student_name"));
            Set<StudentEntity> studentEntitySet = new HashSet<>();
            studentEntitySet.add(student);
            coordinator.setStudents(new HashSet<>(studentEntitySet));
        }
        return coordinator;
    }

    /**
     * Parses a CoordinatorEntity without students from a ResultSet.
     *
     * @param result the ResultSet containing coordinator data
     * @return the CoordinatorEntity
     * @throws SQLException if an error occurs while accessing the ResultSet
     */
    public CoordinatorEntity parseCoordinatorWithoutStudentsFromResultSet(ResultSet result)
            throws SQLException {
        CoordinatorEntity coordinator = new CoordinatorEntity();
        coordinator.setId(Integer.parseInt(result.getString("coordinator_id")));
        coordinator.setName(result.getString("name"));
        return coordinator;
    }
}
