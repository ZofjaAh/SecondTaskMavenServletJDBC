package com.aston.secondTask.infrastructure.repository;

import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.infrastructure.configuration.DateBaseConnectionCreator;
import com.aston.secondTask.infrastructure.repository.queries.CourseSQLQuery;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.exeptions.ProcessingException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
/**
 * Repository for handling course-related database operations.
 */

@AllArgsConstructor
@Slf4j
@Setter
public class CourseRepository extends DateBaseConnectionCreator implements CourseDAO {

    DateBaseConnectionCreator dateBaseConnectionCreator;

    /**
     * Find all courses.
     *
     * @return a set of CourseEntity objects
     * @throws ProcessingException if an error occurs while executing the SQL query
     */
    @Override
    public Set<CourseEntity> findAll() {
        Set<CourseEntity> courseEntitySet = new HashSet<>();
        try (Connection connection = dateBaseConnectionCreator.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     CourseSQLQuery.GET_ALL_COURSES.getQUERY())) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    CourseEntity course = parseCourseFromResultSet(result);
                    courseEntitySet.add(course);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException with loading all courses [{}]", e.getMessage());

            throw new ProcessingException(e.getMessage());
        }
        return courseEntitySet;
    }

    /**
     * Creates a new course.
     *
     * @param course the course entity
     * @return the generated ID of the new course
     * @throws ProcessingException if an error occurs while executing the SQL query
     */
    @Override
    public int createCourse(CourseEntity course) {
        try (Connection connection = dateBaseConnectionCreator.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     CourseSQLQuery.CREATE_COURSE.getQUERY(),
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, course.getName());
            statement.executeUpdate();
            try (ResultSet result = statement.getGeneratedKeys()) {
                result.next();
                int id = result.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            log.error("SQLException with creation course with name: [{}] - [{}]",
                    course.getName(), e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
    }
    /**
     * Updates the name of a course.
     *
     * @param courseId the ID of the course to be updated
     * @param courseName the new name of the course
     * @return the number of rows updated
     * @throws ProcessingException if an error occurs while executing the SQL query
     */

    @Override
    public int updateCourseName(int courseId, String courseName) {
        int rowsUpdated = 0;
        try (Connection connection = dateBaseConnectionCreator.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     CourseSQLQuery.UPDATE_COURSE_NAME_BY_ID.getQUERY())) {
            statement.setString(1, courseName);
            statement.setInt(2, courseId);
            rowsUpdated = statement.executeUpdate();

        } catch (SQLException e) {
            log.error("SQLException with changing course's name with Id: [{}] - [{}]",
                    courseId, e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
        return rowsUpdated;
    }

    /**
     * Deletes a course by ID.
     *
     * @param courseId the ID of the course
     * @return the number of rows updated
     * @throws ProcessingException if an error occurs while executing the SQL query
     */
    @Override
    public int deleteCourse(int courseId) {
        int updated_rows = 0;

        try {
            Connection connection = dateBaseConnectionCreator.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement delete_stud_course_statement = connection.prepareStatement(
                    CourseSQLQuery.DELETE_STUDENT_COURSE_BY_ID.getQUERY());
                 PreparedStatement delete_course_statement = connection.prepareStatement(
                         CourseSQLQuery.DELETE_COURSE_BY_ID.getQUERY())
            ) {
                delete_stud_course_statement.setLong(1, courseId);
                delete_course_statement.setLong(1, courseId);
                updated_rows += delete_stud_course_statement.executeUpdate();
                updated_rows += delete_course_statement.executeUpdate();

            } catch (SQLException e) {
                log.error("SQLException with deleting student_course with Id: [{}] - [{}]",
                        courseId, e.getMessage());

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
     * Parses a CourseEntity from a ResultSet.
     *
     * @param result the ResultSet containing course data
     * @return a CourseEntity
     * @throws SQLException if an error occurs while accessing the ResultSet
     */
    public CourseEntity parseCourseFromResultSet(ResultSet result) throws SQLException {
        CourseEntity course = new CourseEntity();
        course.setId(Integer.parseInt(result.getString("course_id")));
        course.setName(result.getString("name"));
        return course;
    }

}
