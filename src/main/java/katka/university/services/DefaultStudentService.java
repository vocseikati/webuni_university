package katka.university.services;

import java.util.List;
import katka.university.entities.Student;
import katka.university.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultStudentService implements StudentService{

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CentralEducationSystemService educationSystemService;

  @Override
  @Scheduled(cron = "${university.freeSemesterUpdater.cron}")
  public void updateFreeSemesters() {
    List<Student> students = studentRepository.findAll();
    students.forEach(student -> {
      System.out.format("Get number of free semesters of student %s%n", student.getName());
      try {
        Integer educationalId = student.getEducationalId();
        if (educationalId != null) {
          int numberFreeSemesters = educationSystemService.getNumberFreeSemesters(educationalId);
          student.setFreeSemesterNumber(numberFreeSemesters);
          studentRepository.save(student);
        }
      } catch (Exception ex) {
        log.error("Error calling central education system.", ex);
      }
    });
  }
}
