package katka.university.entities;

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
@Builder
public class Teacher extends Person {

  @ManyToMany(mappedBy = "teachers")
  private Set<Course> courses;

}
