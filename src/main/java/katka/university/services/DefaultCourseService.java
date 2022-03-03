package katka.university.services;

import com.querydsl.core.types.Predicate;
import java.util.List;
import katka.university.entities.Course;
import katka.university.entities.QCourse;
import katka.university.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCourseService implements CourseService {

  @Autowired
  private CourseRepository courseRepository;

  @Override
  public Course createCourse(Course course) {
    return courseRepository.save(course);
  }

  @Override
  @Transactional
  @Cacheable("courseSearchResults")
  public List<Course> searchWithRelationShips(Predicate predicate, Pageable pageable) {
//    courses = courseRepository.findAll(predicate, "Course.students");
//    courses = courseRepository.findAll(QCourse.course.in(courses), "Course.teachers");
    List<Course> courses = courseRepository.findAll(predicate, pageable).getContent();
    courses =
        courseRepository.findAll(QCourse.course.in(courses), "Course.teachers", Sort.unsorted());
    courses =
        courseRepository.findAll(QCourse.course.in(courses), "Course.students", pageable.getSort());
    return courses;
  }

  @Override
  public List<Course> getAll(Predicate predicate, Pageable pageable) {
    return courseRepository.findAll(predicate, pageable).getContent();
  }

  @Override
  public List<Course> getAll() {
    return courseRepository.findAll();
  }
}
