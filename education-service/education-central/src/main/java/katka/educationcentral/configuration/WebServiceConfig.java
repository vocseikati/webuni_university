package katka.educationcentral.configuration;

import javax.xml.ws.Endpoint;
import katka.educationcentral.educationservice.xmlws.StudentXmlWs;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {

  private final Bus bus;
  private final StudentXmlWs studentXmlWs;

  @Bean
  public Endpoint endpoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, studentXmlWs);
    endpoint.publish("/student");
    return endpoint;
  }
}
