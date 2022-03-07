package katka.university.controllers;

import hu.webuni.university.api.StudentControllerApi;
import katka.university.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi {

  private final StudentService studentService;

  @Override
  public ResponseEntity<Resource> getProfilePicture(Integer id) {
    Resource profilePicture = studentService.getProfilePicture(id);
    return ResponseEntity.ok(profilePicture);
  }

  @Override
  public ResponseEntity<Void> uploadProfilePictureForStudent(Integer id, MultipartFile content) {
    studentService.saveProfilePicture(id, content);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deleteProfilePicture(Integer id) {
    studentService.deleteProfilePicture(id);
    return ResponseEntity.ok().build();
  }
}
