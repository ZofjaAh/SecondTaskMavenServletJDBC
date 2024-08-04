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
                .students(Set.of(student_2_0(),student_3_0()))
                .build();

    }
    public static StudentEntity student_1_0() {
        return StudentEntity.builder()
                .id(1)
                .name("Ivan Goodhear")
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
        return   CoordinatorEntity.builder()
                .name("Betchowen")
                .build();

    }

    public static CourseEntity course_1() {
        return CourseEntity.builder()
                .name("Voicing").build();
    }
}
