package katka.university.services;

import hu.webuni.university.api.model.CourseStatisticDto;
import java.util.ArrayList;
import java.util.List;
import katka.university.entities.Course;
import katka.university.entities.CourseStatistic;
import katka.university.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final CourseRepository courseRepository;

  public List<CourseStatistic> getCourseStatistic() {
    System.out.println(
        "Report service getCourseStatistic called at thread " + Thread.currentThread().getName());
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return courseRepository.getCourseStatistic();
  }

}
