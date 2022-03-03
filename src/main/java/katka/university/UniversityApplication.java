package katka.university;

import katka.university.services.InitDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@RequiredArgsConstructor
@EnableAsync
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class UniversityApplication implements CommandLineRunner {

  @Autowired
  private InitDbService initDbService;

  public static void main(String[] args) {
    SpringApplication.run(UniversityApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    initDbService.deleteDb();
    initDbService.deleteAudTables();
    initDbService.addInitData();
  }
}
