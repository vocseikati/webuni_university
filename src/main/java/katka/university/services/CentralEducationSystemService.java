package katka.university.services;

import java.util.Random;
import katka.educationcentral.educationservice.xmlws.StudentXmlWsImpl;
import katka.university.aspect.Retry;
import org.springframework.stereotype.Service;

@Service
public class CentralEducationSystemService {

  private final Random random = new Random();

  @Retry(times = 5, waitTime = 500)
  public int getNumberFreeSemesters(int educationalId){
//    int rand = random.nextInt(0, 2);
//    if (rand == 0){
//      throw new RuntimeException("Central Education Service timed out.");
//    }else {
//      return random.nextInt(0,10);
//    }
    return new StudentXmlWsImpl().getNumberFreeSemesters(educationalId);
  }
}
