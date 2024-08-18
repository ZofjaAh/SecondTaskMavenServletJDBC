package util;

import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;

import java.util.Set;

public class DTOFixtures {
    public static StudentDTO student_3_0() {
        return StudentDTO.builder()
                .name("Nikol BadVoice")
                .coordinatorId(1)
                .build();
    }

    public static CoordinatorDTO coordinator_1() {
        return CoordinatorDTO.builder()
                .name("Stefan Dancer")
                .build();
    }
     public static CoordinatorDTO coordinator_1_0() {
        return CoordinatorDTO.builder()
                .id(1)
                .name("Betchowen")
                .build();
    }
    public static CoordinatorDTO coordinator_2_0() {
        return CoordinatorDTO.builder()
                .id(2)
                .name("Mocart")
                .build();
    }

    public static CoordinatorDTO.StudentDTO student_2_0() {
        return CoordinatorDTO.StudentDTO.builder()
                .id(2)
                .name("Nikol BadVoice")
                .build();
    }

    public static CourseDTO course_1() {
        return CourseDTO.builder()
                .name("Voicing")
                .build();
    }
    public static CourseDTO course_1_0() {
        return CourseDTO.builder()
                .id(1)
                .name("Voicing")
                .build();
    }
    public static StudentDTO.CourseDTO course_1_3() {
        return StudentDTO.CourseDTO.builder()
                .id(1)
                .name("Voicing")
                .build();
    }
    public static StudentDTO.CourseDTO course_1_2() {
        return StudentDTO.CourseDTO.builder()
                .id(1)
                .name("Voicing")
                .build();
    }
    public static CourseDTO course_2_0() {
        return CourseDTO.builder()
                .id(2)
                .name("Play Chess")
                .build();
    }
    public static StudentDTO.CourseDTO course_2_3() {
        return StudentDTO.CourseDTO.builder()
                .id(2)
                .name("Play Chess")
                .build();
    }

    public static StudentDTO student_1() {
            return StudentDTO.builder()
                    .name("Aga GoodPlayer")
                    .build();
        }

    public static StudentDTO student_1_0() {
        return StudentDTO.builder()
                .id(1)
                .name("Ivan Goodhear")
                .coordinatorId(1)
                .build();
    }
    public static StudentDTO student_1_1() {
        return StudentDTO.builder()
                .id(1)
                .name("Ivan Goodhear")
                .coordinatorId(1)
                .courses(Set.of(course_1_2()))
                .build();
    }
}

