package katka.university.services;

import com.querydsl.core.types.Predicate;
import java.util.List;
import katka.university.entities.Course;

public interface CourseService {

  List<Course> searchWithRelationShip(Predicate predicate);


}
