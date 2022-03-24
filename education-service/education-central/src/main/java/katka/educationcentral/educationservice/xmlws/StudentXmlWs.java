package katka.educationcentral.educationservice.xmlws;

import javax.jws.WebService;

@WebService
public interface StudentXmlWs {

  int getNumberFreeSemesters(int educationalId);
}
