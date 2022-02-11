package katka.university.repositories;

import katka.university.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CourseRepository extends JpaRepository<Course, Integer>,
    QuerydslPredicateExecutor<Course> {
}
