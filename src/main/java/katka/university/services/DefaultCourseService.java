package katka.university.services;

import com.querydsl.core.types.Predicate;
import java.util.List;
import katka.university.entities.Course;
import katka.university.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCourseService implements CourseService{

  @Autowired
  private CourseRepository courseRepository;

  @Override
  public List<Course> searchWithRelationShip(Predicate predicate) {
    return null;
  }
}
