package katka.university.entities;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryData<T> {

  private T data;
  private RevisionType revisionType;
  private int revision;
  private Date date;
}
