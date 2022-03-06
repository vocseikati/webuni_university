package katka.university.mapper;

import hu.webuni.university.api.model.HistoryDataCourseDto;
import katka.university.configurations.DateTimeMapper;
import katka.university.entities.Course;
import katka.university.entities.HistoryData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface HistoryDataMapper {

  HistoryDataCourseDto courseHistoryDataToDto(HistoryData<Course> historyData);
}
