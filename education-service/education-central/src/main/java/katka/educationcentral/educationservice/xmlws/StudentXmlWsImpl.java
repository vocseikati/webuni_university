package katka.educationcentral.educationservice.xmlws;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentXmlWsImpl implements StudentXmlWs {

  private final Random random = new Random();

  @Override
  public int getNumberFreeSemesters(int educationalId) {
    return random.nextInt(0,10);
  }
}
