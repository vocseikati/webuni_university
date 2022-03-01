package katka.university.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
public class Teacher extends Person{

  @ManyToMany(mappedBy = "teachers")
  private Set<Course> courses;

  @Builder
  public Teacher(int id, String name, LocalDate birthdate) {
    super(id, name, birthdate);
  }
}
