package katka.university.controllers;

import java.util.List;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import javax.validation.Valid;
import katka.university.dtos.CourseDto;
import katka.university.entities.Course;
import katka.university.mapper.CourseMapper;
import katka.university.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
public class CourseController {

  @Autowired
  private final CourseService courseService;

  @Autowired
  private final CourseMapper courseMapper;

  @GetMapping
  public List<CourseDto> getAllCourses() {
    List<Course> courses = courseService.getAll();
    return courseMapper.coursesToDtos(courses);
  }

  @PostMapping
  public CourseDto createCourse(@RequestBody @Valid CourseDto courseDto) {
    Course course = courseService.createCourse(courseMapper.dtoToCourse(courseDto));
    return courseMapper.courseToDto(course);
  }

  @GetMapping("/search")
  public List<CourseDto> search(@QuerydslPredicate(root = Course.class) Predicate predicate,
                                @RequestParam Optional<Boolean> full,
                                @SortDefault("id") Pageable pageable) {
    Boolean isFull = full.orElse(false);
    if (isFull) {
      Iterable<Course> courses = courseService.searchWithRelationShips(predicate, pageable);
      return courseMapper.coursesToDtos(courses);
    }else {
      Iterable<Course> courses = courseService.getAll(predicate, pageable);
      return courseMapper.courseSummariesToDtos(courses);
    }
  }
}
