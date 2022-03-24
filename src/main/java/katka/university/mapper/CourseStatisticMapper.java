package katka.university.mapper;

import hu.webuni.university.api.model.CourseStatisticDto;
import java.util.List;
import katka.university.entities.CourseStatistic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseStatisticMapper {

  List<CourseStatisticDto> courseStatisticsToDtos(List<CourseStatistic> courseStatistics);
}
