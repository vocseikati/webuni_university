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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitDbService {

  @Autowired
  private CourseRepository courseRepository;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private TeacherRepository teacherRepository;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional
  public void deleteDb() {
    courseRepository.deleteAll();
    studentRepository.deleteAll();
    teacherRepository.deleteAll();
  }

  @Transactional
  public void deleteAudTables(){
    jdbcTemplate.update("DELETE FROM course_aud");
    jdbcTemplate.update("DELETE FROM course_students_aud");
    jdbcTemplate.update("DELETE FROM course_teachers_aud");
    jdbcTemplate.update("DELETE FROM student_aud");
    jdbcTemplate.update("DELETE FROM teacher_aud");
    jdbcTemplate.update("DELETE FROM revinfo");
  }

  @Transactional
  public void addInitData() {
    Student student1 = saveNewStudent(LocalDate.of(2000, 10, 10),"student1",  1);
    Student student2 = saveNewStudent(LocalDate.of(2000, 10, 10),"student2",  2);
    Student student3 = saveNewStudent(LocalDate.of(2000, 10, 10), "student3", 3);

    Teacher teacher1 = saveNewTeacher("teacher1", LocalDate.of(2000, 10, 10));
    Teacher teacher2 = saveNewTeacher("teacher2", LocalDate.of(2000, 10, 10));
    Teacher teacher3 = saveNewTeacher("teacher3", LocalDate.of(2000, 10, 10));

    createCourse("course1", Arrays.asList(teacher1, teacher2),
        Arrays.asList(student1, student2, student3));
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

  private Student saveNewStudent(LocalDate birthdate, String name, int semester) {
    return studentRepository.save(
        Student.builder()
            .birthdate(birthdate)
            .name(name)
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
