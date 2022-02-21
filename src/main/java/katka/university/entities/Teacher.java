package katka.university.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends Person {

  @ManyToMany(mappedBy = "teachers")
  private Set<Course> courses;

  @Builder
  public Teacher(int id, String name, LocalDate birthdate) {
    super(id, name, birthdate);
  }
}
