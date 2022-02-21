package katka.university.repositories;

import katka.university.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;

public interface CourseRepository extends JpaRepository<Course, Integer>,
    QuerydslPredicateExecutor<Course>, QuerydslBinderCustomizer<QCourse> {
}
