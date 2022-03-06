package katka.university.controllers;

import com.querydsl.core.types.Predicate;
import hu.webuni.university.api.CourseControllerApi;
import hu.webuni.university.api.model.CourseDto;
import hu.webuni.university.api.model.HistoryDataCourseDto;
import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import katka.university.entities.Course;
import katka.university.entities.HistoryData;
import katka.university.mapper.CourseMapper;
import katka.university.mapper.HistoryDataMapper;
import katka.university.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseControllerApi {

  private final CourseService courseService;
  private final CourseMapper courseMapper;
  private final HistoryDataMapper historyDataMapper;
  private final NativeWebRequest nativeWebRequest;
  private final PageableHandlerMethodArgumentResolver pageableResolver;
  private final QuerydslPredicateArgumentResolver predicateResolver;

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.of(nativeWebRequest);
  }

  @Override
  public ResponseEntity<CourseDto> createCourse(CourseDto courseDto) { //todo @Valid?
    Course course = courseService.createCourse(courseMapper.dtoToCourse(courseDto));
    return ResponseEntity.ok(courseMapper.courseToDto(course));
  }

  @Override
  public ResponseEntity<List<CourseDto>> getAllCourses() {
    List<Course> courses = courseService.getAll();
    return ResponseEntity.ok(courseMapper.coursesToDtos(courses));
  }

  @Override
  public ResponseEntity<List<HistoryDataCourseDto>> getCourseHistoryById(Integer id) {
    List<HistoryData<Course>> courses = courseService.getCourseHistory(id);
    List<HistoryDataCourseDto> courseDtosWithHistory = new ArrayList<>();
    for (HistoryData<Course> course : courses) {
      courseDtosWithHistory.add(historyDataMapper.courseHistoryDataToDto(course));
    }
    return ResponseEntity.ok(courseDtosWithHistory);
  }

  @Override
  public ResponseEntity<CourseDto> getVersionAt(Integer id, OffsetDateTime at) {
    return ResponseEntity.ok(courseMapper.courseToDto(courseService.getVersionAtById(id, at)));
  }

  @Override
  public ResponseEntity<CourseDto> modifyCourse(Integer id, CourseDto courseDto) {
    Course course = courseService.modifyCourse(courseMapper.dtoToCourse(courseDto), id);
    return ResponseEntity.ok(courseMapper.courseSummaryToDto(course));
  }

  @Override
  public ResponseEntity<List<CourseDto>> search(Boolean full, Integer id, String name,
                                                String teachersName, String studentsId,
                                                List<Integer> studentsSemester, Integer page,
                                                Integer size, List<String> sort) {
    boolean isFull = full != null && full;
    Pageable pageable = createPageable("configPageable");
    Predicate predicate = createPredicate("configPredicate");

    if (isFull) {
      Iterable<Course> courses = courseService.searchWithRelationShips(predicate, pageable);
      return ResponseEntity.ok(courseMapper.coursesToDtos(courses));
    } else {
      Iterable<Course> courses = courseService.getAll(predicate, pageable);
      return ResponseEntity.ok(courseMapper.courseSummariesToDtos(courses));
    }
  }

  private Predicate createPredicate(String configMethodName) {
    Method method;
    try {
      ModelAndViewContainer mavContainer = null;
      WebDataBinderFactory binderFactory = null;
      method = this.getClass().getMethod(configMethodName, Predicate.class);
      MethodParameter methodParameter = new MethodParameter(method, 0);
      return (Predicate) predicateResolver.resolveArgument(methodParameter, mavContainer,
          nativeWebRequest, binderFactory);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private Pageable createPageable(String pageableConfigurerMethodName) {
    ModelAndViewContainer mavContainer = null;
    WebDataBinderFactory binderFactory = null;
    Method method;
    try {
      method = this.getClass().getMethod(pageableConfigurerMethodName, Pageable.class);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    MethodParameter methodParameter = new MethodParameter(method, 0);
    return pageableResolver.resolveArgument(methodParameter, mavContainer, nativeWebRequest,
        binderFactory);
  }

  public void configPageable(@SortDefault("id") Pageable pageable) {
  }

  public void configPredicate(@QuerydslPredicate(root = Course.class) Predicate predicate) {
  }
}
