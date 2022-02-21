package katka.university.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TeacherDto {

  private int id;
  private String name;
  private LocalDate birthdate;

}
