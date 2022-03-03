package katka.university.services;

import com.querydsl.core.types.Predicate;
import java.util.List;
import katka.university.entities.Course;
import katka.university.entities.HistoryData;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  Course createCourse(Course course);

  List<Course> searchWithRelationShips(Predicate predicate, Pageable pageable);

  List<Course> getAll(Predicate predicate, Pageable pageable);

  List<Course> getAll();

  List<HistoryData<Course>> getCourseHistory(int id);

  Course modifyCourse(Course course, int courseId);
}
