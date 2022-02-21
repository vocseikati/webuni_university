package katka.university.services;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import katka.university.entities.Course;
import katka.university.entities.Student;
import katka.university.entities.Teacher;
import katka.university.repositories.CourseRepository;
import katka.university.repositories.StudentRepository;
import katka.university.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InitDbService {

  private final CourseRepository courseRepository;
  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;

  @Transactional
  public void deleteDb() {

    courseRepository.deleteAll();
    studentRepository.deleteAll();
    teacherRepository.deleteAll();
  }

  @Transactional
  public void addInitData() {
    Student student1 = saveNewStudent("student1", LocalDate.of(2000, 10, 10), 1, 111);
    Student student2 = saveNewStudent("student2", LocalDate.of(2000, 10, 10), 2, 222);
    Student student3 = saveNewStudent("student3", LocalDate.of(2000, 10, 10), 3, 333);

    Teacher teacher1 = saveNewTeacher("teacher1", LocalDate.of(2000, 10, 10));
    Teacher teacher2 = saveNewTeacher("teacher2", LocalDate.of(2000, 10, 10));
    Teacher teacher3 = saveNewTeacher("teacher3", LocalDate.of(2000, 10, 10));

    createCourse("course1", Arrays.asList(teacher1, teacher2), Arrays.asList(student1, student2, student3));
    createCourse("course2", Arrays.asList(teacher2), Arrays.asList(student1, student3));
    createCourse("course3", Arrays.asList(teacher1, teacher3), Arrays.asList(student2, student3));
  }

  private Course createCourse(String name, List<Teacher> teachers, List<Student> students) {
    return courseRepository.save(
        Course.builder()
            .name(name)
            .teachers(new HashSet<>(teachers))
            .students(new HashSet<>(students))
            .build());
  }

  private Student saveNewStudent(String name, LocalDate birthdate, int semester, int eduId) {
    return studentRepository.save(
        Student.builder()
            .name(name)
            .birthdate(birthdate)
            .semester(semester)
            .build());
  }

  private Teacher saveNewTeacher(String name, LocalDate birthdate) {
    return teacherRepository.save(
        Teacher.builder()
            .name(name)
            .birthdate(birthdate)
            .build());
  }

}
