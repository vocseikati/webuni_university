package katka.university.services;

import com.querydsl.core.types.Predicate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import katka.university.entities.Course;
import katka.university.entities.HistoryData;
import katka.university.entities.QCourse;
import katka.university.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
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

  @Autowired
  private EntityManager entityManager;

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

  @Override
  @Transactional
  public Course modifyCourse(Course course, int courseId) {
    Course originalCourse = getCourse(courseId);
    if (originalCourse.getTeachers() != null){
      course.setTeachers(originalCourse.getTeachers());
    }
    if (originalCourse.getStudents() != null){
      course.setStudents(originalCourse.getStudents());
    }
    course.setId(courseId);
    return courseRepository.save(course);
  }

  @Transactional
  @SuppressWarnings({"rawtypes", "unchecked"})
  public List<HistoryData<Course>> getCourseHistory(int id) {
    return AuditReaderFactory.get(entityManager)
        .createQuery()
        .forRevisionsOfEntity(Course.class, false, true)
        .add(AuditEntity.property("id").eq(id))
        .getResultList()
        .stream()
        .map(o -> {
          Object[] objArray = (Object[]) o;
          DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) objArray[1];
          Course course = (Course) objArray[0];
          course.getStudents().size();
          course.getTeachers().size();
          return new HistoryData<>(
              course,
              (RevisionType) objArray[2],
              revisionEntity.getId(),
              revisionEntity.getRevisionDate()
          );
        }).toList();
  }

  @Transactional
  @SuppressWarnings("rawtypes")
  public Course getVersionAtById(int id, OffsetDateTime when){
    long epochMilli = when.toInstant().toEpochMilli();
    List resultList = AuditReaderFactory.get(entityManager)
        .createQuery()
        .forRevisionsOfEntity(Course.class, true, false)
        .add(AuditEntity.property("id").eq(id))
        .add(AuditEntity.revisionProperty("timestamp").le(epochMilli))
        .addOrder(AuditEntity.revisionProperty("timestamp").desc())
        .setMaxResults(1)
        .getResultList();
    if (!resultList.isEmpty()){
      Course course = (Course) resultList.get(0);
      course.getStudents().size();
      course.getTeachers().size();
      return course;
    }
    return null;
  }

  private Course getCourse(int courseId) {
    Optional<Course> byId = courseRepository.findById(courseId);
    if (byId.isEmpty()){
      throw new EntityNotFoundException("There is no course with the provided id.");
    }
    return byId.get();
  }
}
