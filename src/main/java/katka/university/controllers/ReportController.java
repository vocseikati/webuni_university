package katka.university.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import katka.university.entities.Course;
import katka.university.entities.CourseStatistic;
import katka.university.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController{

  private final ReportService reportService;

  @GetMapping("/averageSemestersPerCourse")
  @Async
  public CompletableFuture<List<CourseStatistic>> showSemesterReport() {
    List<CourseStatistic> courseStatistic = reportService.getCourseStatistic();
    return CompletableFuture.completedFuture(courseStatistic);
  }

}
