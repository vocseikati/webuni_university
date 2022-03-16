package katka.university.entities;

import lombok.Data;

@Data
public class CourseStatistic {

  private int courseId;
  private String courseName;
  private double averageSemesterOfStudents;

}
