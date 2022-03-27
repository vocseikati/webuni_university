package katka.university.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import katka.university.entities.Student;
import katka.university.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class DefaultStudentService implements StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CentralEducationSystemService educationSystemService;

  @Value("${university.content.profilePictures}")
  private String profilePicsFolder;   //configproperties osztályba is lehet rakni

  @PostConstruct
  public void init() {
    if (Files.exists(Path.of(profilePicsFolder))) {
      return;
    }
    try {
      Files.createDirectory(Path.of(profilePicsFolder));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
//  @Scheduled(cron = "${university.freeSemesterUpdater.cron}")
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

  @Override
  public Resource getProfilePicture(Integer studentId) {
    getStudent(studentId);
    FileSystemResource fileSystemResource = new FileSystemResource(getPath(studentId));
    if (!fileSystemResource.exists()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return fileSystemResource;
  }

  @Override
  public void saveProfilePicture(Integer id, MultipartFile content) {
    getStudent(id);
    try {
      InputStream inputStream = content.getInputStream();
      Files.copy(inputStream, getPath(id), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteProfilePicture(Integer studentId) {
    getStudent(studentId);
    DirectoryStream<Path> dirStream = null;
    try {
      dirStream = Files.newDirectoryStream(Path.of(profilePicsFolder));

      for (Path path : dirStream) {
        String fullFilename = path.getFileName().toString();
        int i = fullFilename.indexOf(".");
        String filename = fullFilename.substring(0, i);
        if (filename.equals(studentId.toString())) {
          Files.deleteIfExists(path);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Transactional
  public void updateBalance(int studentId, int amount) {
//    Student student = getStudent(studentId);
//    student.setBalance(student.getBalance() + amount);
    studentRepository.findById(studentId).ifPresent(s -> s.setBalance(s.getBalance() + amount));
  }

  private Path getPath(Integer studentId) {
    return Paths.get(profilePicsFolder, studentId + ".jpg");
  }

  private Student getStudent(int studentId) {
    Optional<Student> byId = studentRepository.findById(studentId);
    if (byId.isEmpty()) {
      throw new EntityNotFoundException("There is no student with the provided id.");
    }
    return byId.get();
  }
}
