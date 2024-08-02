package com.aston.secondTask.infrastructure.repository;

import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.infrastructure.configuration.SessionManager;
import com.aston.secondTask.infrastructure.repository.mapper.ResultSetMapper;
import com.aston.secondTask.infrastructure.repository.queries.CourseSQLQuery;
import com.aston.secondTask.service.DAO.CourseDAO;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Slf4j
@Setter
public class CourseRepository implements CourseDAO {

    private final SessionManager sessionManager;
    private final ResultSetMapper resultSetMapper;

    @Override
    public Set<CourseEntity> findAll() throws SQLException {
        Set<CourseEntity> courseEntitySet = new HashSet<>();
        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(
                     CourseSQLQuery.GET_ALL_COURSES.getQUERY())) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    CourseEntity course = resultSetMapper.parseCourseFromResultSet(result);
                    courseEntitySet.add(course);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException with loading all courses [{}]", e.getMessage());;
            sessionManager.rollbackSession();
            throw e;
        }
        return  courseEntitySet;
    }

    @Override
    public int createCourse(CourseEntity course) throws SQLException {
        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(
                     CourseSQLQuery.CREATE_COURSE.getQUERY(),
                     Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, course.getName());
            statement.executeUpdate();
            try (ResultSet result = statement.getGeneratedKeys()) {
                result.next();
                int id = result.getInt(1);
                sessionManager.commitSession();
                return id;
            }
        } catch (SQLException e) {
            log.error("SQLException with creation course with name: [{}] - [{}]",
                    course.getName(), e.getMessage());
            sessionManager.rollbackSession();
            throw e;
        }
    }

    @Override
    public int updateCourseName(int courseId, String courseName) throws SQLException {
        int rowsUpdated = 0;
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(
                     CourseSQLQuery.UPDATE_COURSE_NAME_BY_ID.getQUERY())) {
            statement.setString(1, courseName);
            statement.setInt(2, courseId);
            rowsUpdated = statement.executeUpdate();
            sessionManager.commitSession();

        } catch (SQLException e) {
            log.error("SQLException with changing course's name with Id: [{}] - [{}]",
                    courseId, e.getMessage());
            throw e;
        }
        return rowsUpdated;
    }

    @Override
    public int deleteCourse(int courseId) throws SQLException {
        int updated_rows;

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(
                     CourseSQLQuery.DELETE_COURSE_BY_ID.getQUERY())) {
            statement.setLong(1, courseId);
            statement.setLong(2, courseId);
            updated_rows = statement.executeUpdate();
            sessionManager.commitSession();

        } catch (SQLException e) {
            log.error("SQLException with deleting course with Id: [{}] - [{}]",
                    courseId, e.getMessage());;
            sessionManager.rollbackSession();
            throw e;
        }
        return updated_rows;
    }

}
