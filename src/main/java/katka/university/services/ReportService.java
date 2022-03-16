//package katka.university.services;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import katka.university.entities.CourseStatistic;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ReportService {
//
//  public CompletableFuture<List<CourseStatistic>> getCourseStatistic() {
//    System.out.println(
//        "Report service getCourseStatistic called at thread " + Thread.currentThread().getName());
//    try {
//      Thread.sleep(5000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    return CompletableFuture.completedFuture()
//  }
//}
