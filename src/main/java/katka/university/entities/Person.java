package katka.university.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@MappedSuperclass
@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public abstract class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ToString.Include
  @EqualsAndHashCode.Include
  private int id;
  @ToString.Include
  private String name;
  private LocalDate birthdate;
}
