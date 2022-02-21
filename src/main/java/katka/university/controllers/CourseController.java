package katka.university.controllers;

import java.util.List;
import java.util.function.Predicate;
import javax.validation.Valid;
import katka.university.dtos.CourseDto;
import katka.university.entities.Course;
import katka.university.mapper.CourseMapper;
import katka.university.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
public class CourseController {

  @Autowired
  private final CourseService courseService;

  @Autowired
  private final CourseMapper courseMapper;

  @PostMapping
  public CourseDto createCourse(@RequestBody @Valid CourseDto courseDto){
    Course course = courseService.createCourse(courseMapper.dtoToCourse(courseDto));
    return courseMapper.courseToDto(course);
  }

  @GetMapping("/search")
  public List<CourseDto> search(@QuerydslPredicate(root = Course.class)Predicate predicate){

  }
}
