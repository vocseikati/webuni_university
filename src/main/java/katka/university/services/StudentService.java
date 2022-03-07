package katka.university.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {

  void updateFreeSemesters();

  Resource getProfilePicture(Integer id);

  void saveProfilePicture(Integer id, MultipartFile content);

  void deleteProfilePicture(Integer id);
}
