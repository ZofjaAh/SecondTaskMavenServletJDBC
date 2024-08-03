package com.aston.secondTask.infrastructure.repository;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.infrastructure.configuration.DateBaseConnectionCreator;
import com.aston.secondTask.infrastructure.repository.mapper.ResultSetMapper;
import com.aston.secondTask.infrastructure.repository.queries.StudentSQLQuery;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.aston.secondTask.service.exeptions.NotFoundException;
import com.aston.secondTask.service.exeptions.ProcessingException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Setter
public class StudentRepository  extends DateBaseConnectionCreator implements StudentDAO {


    @Override
    public int createStudentWithCoordinator(StudentEntity studentEntity, int coordinatorId)  {
        int student_Id;

        try (PreparedStatement statement = getConnection().prepareStatement(
                     StudentSQLQuery.CREATE_STUDENT_WITH_COORDINATOR.getQUERY(),
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, studentEntity.getName());
            statement.setInt(2, coordinatorId);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                student_Id = rs.getInt(1);

            }
        } catch (SQLException e) {
            log.error("SQLException with creation Student with Name:[{}] - [{}]",
                    studentEntity.getName(), e.getMessage());

            throw new ProcessingException(e.getMessage());
        }

        return student_Id;
    }

    @Override
    public int deleteStudent(int studentId) throws SQLException {
        int updated_rows;
        try (PreparedStatement statement = getConnection().prepareStatement(
                     StudentSQLQuery.DELETE_STUDENT_BY_ID.getQUERY())) {
            statement.setLong(1, studentId);
            statement.setLong(2, studentId);
            updated_rows = statement.executeUpdate();


        } catch (SQLException e) {
            log.error("SQLException with deleting student with Id: [{}] - [{}]",
                    studentId, e.getMessage());

            throw e;
        }
        return updated_rows;
    }


    @Override
    public int updateCoordinator(int studentId, int coordinatorId)  {
        int rowsUpdated = 0;

        try (PreparedStatement statement = getConnection().prepareStatement(
                     StudentSQLQuery.UPDATE_COORDINATOR_BY_ID.getQUERY())) {
            statement.setInt(1, coordinatorId);
            statement.setInt(2, studentId);
            rowsUpdated = statement.executeUpdate();


        } catch (SQLException e) {
            log.error("SQLException with changing student's coordinator [{}]", e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
        return rowsUpdated;
    }

    @Override
    public int addCourse(int studentId, int courseId)  {
        int rowsUpdated = 0;
        List<StudentEntity> studentEntityList = new ArrayList<>();
        StudentEntity studentEntity = null;
        try (PreparedStatement statement = getConnection().prepareStatement(
                     StudentSQLQuery.GET_STUDENT_WITH_COURSES_BY_ID.getQUERY())) {
            statement.setInt(1, studentId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    StudentEntity student = parseStudentFromResultSet(result);
                    studentEntityList.add(student);
                }
            }
            studentEntity = createStudentEntityFromListStudents(studentEntityList);
        } catch (SQLException e) {
            log.error("SQLException with loading student by Id: [{}] - [{}]", studentId, e.getMessage());

            throw new ProcessingException(e.getMessage());
        }
        if (Objects.nonNull(studentEntity)) {
            if (courseNotAdded(studentEntity, courseId)) {
                try (PreparedStatement statement = getConnection().prepareStatement(
                             StudentSQLQuery.ADD_COURSE.getQUERY())) {
                    statement.setInt(1, studentId);
                    statement.setInt(2, courseId);
                    rowsUpdated = statement.executeUpdate();


                } catch (SQLException e) {
                    log.error("SQLException with changing student's coordinator [{}]", e.getMessage());
                    throw new ProcessingException(e.getMessage());
                }
            } else throw new ProcessingException("Sorry we couldn't add such course, please change it id");
        } else throw new NotFoundException("Student with such id: [{}] doesn't exist");
        return rowsUpdated;
    }

    @Override
    public StudentEntity findById(int studentId)  {
        List<StudentEntity> studentEntityList = new ArrayList<>();
        StudentEntity studentEntity = null;
        try (PreparedStatement statement = getConnection().prepareStatement(
                     StudentSQLQuery.GET_STUDENT_WITH_COURSES_BY_ID.getQUERY())) {
            statement.setInt(1, studentId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    StudentEntity student = parseStudentFromResultSet(result);
                    studentEntityList.add(student);
                }
            }
            studentEntity = createStudentEntityFromListStudents(studentEntityList);
        } catch (SQLException e) {
            log.error("SQLException with loading student by Id: [{}] - [{}]", studentId, e.getMessage());

            throw new ProcessingException(e.getMessage());
        }
        if (Objects.nonNull(studentEntity)) {
            return studentEntity;
        } else {
            log.error("Student with such Id: [{}] doesn't exist", studentId);
            throw new NotFoundException("Sorry, coordinator with such Id: [{}] doesn't exist");
        }
    }

    private StudentEntity createStudentEntityFromListStudents(List<StudentEntity> students) {
        Set<CourseEntity> courseEntitySet = students.stream()
                .flatMap(student -> student.getCourses().stream())
                .collect(Collectors.toSet());
        return students.get(0).withCourses(courseEntitySet);
    }


    private boolean courseNotAdded(StudentEntity studentEntity, int courseId) {
        return
                Objects.isNull(studentEntity.getCourses()) | studentEntity.getCourses()
                        .stream().noneMatch(course -> course.getId() != courseId);

    }
    public StudentEntity parseStudentFromResultSet(ResultSet result) throws SQLException {
        StudentEntity student = new StudentEntity();
        student.setId(Integer.parseInt(result.getString("id")));
        student.setName(result.getString("name"));
        String coordinatorId = result.getString("coordinator_id");
        String courseId = result.getString("curse_id");
        if (coordinatorId != null) {
            CoordinatorEntity coordinator = new CoordinatorEntity();
            coordinator.setId(Integer.parseInt(coordinatorId));
            student.setCoordinator(coordinator);
        }
        if (courseId != null) {
            Set<CourseEntity> courseEntitySet = new HashSet<>();
            CourseEntity course = new CourseEntity();
            course.setId(Integer.parseInt(courseId));
            course.setName(result.getString("name"));
            courseEntitySet.add(course);
            student.setCourses(new HashSet<>(courseEntitySet));
        }

        return student;
    }
}

