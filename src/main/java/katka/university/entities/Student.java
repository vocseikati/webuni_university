package katka.university.entities;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student extends Person{

  private int semester;

  @ManyToMany(mappedBy = "students")
  private Set<Course> courses;
}
