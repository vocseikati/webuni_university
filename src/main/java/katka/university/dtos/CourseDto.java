package katka.university.dtos;

import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class CourseDto {

  private int id;
  private String name;
  private List<StudentDto> students;
  private List<TeacherDto> teachers;
}
