package katka.university.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import katka.university.entities.Course;
import katka.university.entities.QCourse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CourseRepository
    extends JpaRepository<Course, Integer>,
    QuerydslPredicateExecutor<Course>,
    QuerydslBinderCustomizer<QCourse>,
    QuerydslWithEntityGraphRepository<Course> {

  @EntityGraph(attributePaths = {"students", "teachers"})
  List<Course> findAll();

  @Override
  default void customize(QuerydslBindings bindings, QCourse course) {
    bindings.bind(course.name).first((StringExpression::startsWithIgnoreCase));
    bindings.bind(course.teachers.any().name)
        .first(((path, value) -> path.startsWithIgnoreCase(value)));
    bindings.bind(course.students.any().semester).all(((path, values) -> {
      if (values.size() != 2) {
        return Optional.empty();
      }
      Iterator<? extends Integer> iterator = values.iterator();
      Integer from = iterator.next();
      Integer to = iterator.next();
      return Optional.of(path.between(from, to));
    }));
  }


}
