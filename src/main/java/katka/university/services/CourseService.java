package katka.university.services;

import com.querydsl.core.types.Predicate;
import java.util.List;
import katka.university.entities.Course;

public interface CourseService {

  Course createCourse(Course course);

  List<Course> searchWithRelationShip(Predicate predicate);


}
