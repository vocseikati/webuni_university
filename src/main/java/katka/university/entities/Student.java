package katka.university.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Student extends Person {

  private int semester;

  @ManyToMany(mappedBy = "students")
  private Set<Course> courses;

  private Integer educationalId;

  private Integer freeSemesterNumber;

  @Builder
  public Student(int id, String name, LocalDate birthdate, int semester, int educationalId, int freeSemesterNumber) {
    super(id, name, birthdate);
    this.semester = semester;
    this.educationalId = educationalId;
    this.freeSemesterNumber = freeSemesterNumber;
  }
}
