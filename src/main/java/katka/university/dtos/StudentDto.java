package katka.university.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class StudentDto {

  private int id;
  private String name;
  private LocalDate birthdate;
  private int semester;

}
