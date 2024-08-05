package util;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.entities.StudentEntity;

import java.util.Set;

public class EntityFixtures {
    public static CoordinatorEntity coordinatorWithStudents_1() {
        return CoordinatorEntity.builder()
                .id(1)
                .name("Shopen")
                .students(Set.of(student_2_0(), student_3_0()))
                .build();

    }

    public static StudentEntity student_1_0() {
        return StudentEntity.builder()
                .id(1)
                .name("Ivan Goodhear")
                .build();

    }
    public static StudentEntity student_1_1() {
        return StudentEntity.builder()
                .id(1)
                .name("Ivan Goodhear")
                .coordinator(coordinator_1_0())
                .build();

    }

    public static StudentEntity student_2_0() {
        return StudentEntity.builder()
                .id(2)
                .name("Nikol BadVoice")
                .build();

    }

    public static StudentEntity student_3_0() {
        return StudentEntity.builder()
                .id(3)
                .name("Ihor Nothear")
                .build();

    }

    public static CoordinatorEntity coordinator_2() {
        return CoordinatorEntity.builder()
                .name("Betchowen")
                .build();

    }

    public static CourseEntity course_1() {
        return CourseEntity.builder()
                .name("Voicing").build();
    }
    public static CourseEntity course_1_0() {
        return CourseEntity.builder()
                .id(1)
                .name("Voicing").build();
    }

    public static CourseEntity course_2() {
        return CourseEntity.builder()
                .id(2)
                .name("Play pianoforte")
                .build();
    }
    public static CourseEntity course_2_0() {
        return CourseEntity.builder()
                .id(2)
                .name("Play Chess")
                .build();
    }

    public static CourseEntity course_2_1() {
        return CourseEntity.builder()
                .id(2)
                .name("Play guitar")
                .build();
    }

    public static StudentEntity studentWithCourses_1() {
        return StudentEntity.builder()
                .id(1)
                .name("Ivan Goodhear")
                .coordinator(CoordinatorEntity.builder()
                        .id(2)
                        .build())
                .courses(Set.of(course_2_1(), course_2()))
                .build();
    }

    public static StudentEntity student_4() {
        return StudentEntity.builder()
                .name("Ivan Writer")
                .build();
    }

    public static CoordinatorEntity getCoordinator_1() {
        return  CoordinatorEntity.builder().
                name("Stefan Dancer").build();
    }
    public static CoordinatorEntity coordinator_1_0() {
        return CoordinatorEntity.builder()
                .id(1)
                .name("Betchowen")
                .build();

    }
    public static CoordinatorEntity coordinator_2_0() {
        return CoordinatorEntity.builder()
                .id(2)
                .name("Mocart")
                .build();

    }

    public static StudentEntity student_1() {
       return StudentEntity.builder()
                .name("Aga GoodPlayer")
                .build();
    }
}
