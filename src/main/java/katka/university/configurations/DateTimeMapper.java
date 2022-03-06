package katka.university.configurations;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class DateTimeMapper {

  public OffsetDateTime toOffsetDateTime(Timestamp timestamp) {
    if (timestamp != null) {
      return OffsetDateTime.of(timestamp.toLocalDateTime().getYear(),
          timestamp.toLocalDateTime().getMonthValue(),
          timestamp.toLocalDateTime().getDayOfMonth(),
          timestamp.toLocalDateTime().getHour(),
          timestamp.toLocalDateTime().getMinute(),
          timestamp.toLocalDateTime().getSecond(),
          timestamp.toLocalDateTime().getNano(),
          ZoneOffset.UTC);
    } else {
      return null;
    }
  }

  public Timestamp toTimestamp(OffsetDateTime offsetDateTime){
    if(offsetDateTime != null) {
      return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC)
          .toLocalDateTime());
    } else {
      return null;
    }
  }

}
