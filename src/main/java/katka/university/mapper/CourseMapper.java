package katka.university.mapper;

import java.util.List;
import katka.university.dtos.CourseDto;
import katka.university.entities.Course;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CourseMapper {

  CourseDto courseToDto(Course course);

  @Named("summary")
  @Mapping(ignore = true, target = "teachers")
  @Mapping(ignore = true, target = "students")
  CourseDto courseSummaryToDto(Course course);

  Course dtoToCourse(CourseDto courseDto);

  List<CourseDto> coursesToDtos(Iterable<Course> courses);

  @IterableMapping(qualifiedByName = "summary")
  List<CourseDto> courseSummariesToDtos(Iterable<Course> findAll);

}
