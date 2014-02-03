package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import views.formdata.StudentFormData;

/**
 * Represent a Grade Point Average. 
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Some methods to facilitate form display and manipulation (makeGPAMap, etc.).
 * <li> Some fields and methods to "fake" a database of GPAs.
 * </ul>
 */
public class GradePointAverage {
  private long id;
  private String name;

  public GradePointAverage(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Create a map of GPA name -> boolean where the boolean is true if the GPA corresponds to the student.
   * @param student A student with a GPA.
   * @return A map of GPA to boolean indicating which one is the student's GPA.
   */
  public static Map<String, Boolean> makeGPAMap(StudentFormData student) {
    Map<String, Boolean> gpaMap = new TreeMap<String, Boolean>();
    for (GradePointAverage gpa : allGPAs) {
      gpaMap.put(gpa.getName(),  (student == null) ? false : (student.gpa != null && student.gpa.equals(gpa.getName())));
    }
    return gpaMap;
  }

  /**
   * @return A list of GPA ranges in sorted order.
   */
  public static List<String> getGPAList() {
    String[] nameArray = {"4.0", "3.0 - 3.9", "2.0 - 2.9", "1.0 - 1.9"};
    return Arrays.asList(nameArray);
  }

  /**
   * Return the GPA instance in the database with name 'gpa' or null if not found.
   * @param gpaName The gpa
   * @return The GradePointAverage instance, or null.
   */
  public static GradePointAverage findGPA(String gpaName) {
    for (GradePointAverage gpa : allGPAs) {
      if (gpaName.equals(gpa.getName())) {
        return gpa;
      }
    }
    return null;
  }

  /**
   * Define a default GPA, used for form display.
   * @return The default GPA.
   */
  public static GradePointAverage getDefaultGPA() {
    return findGPA("4.0");
  }

  @Override
  public String toString() {
    return String.format("[GPA %s]", this.name);
  }

  /** Fake a database of GPAs. */
  private static List<GradePointAverage> allGPAs = new ArrayList<GradePointAverage>();

  /** Instantiate the fake database of GPAs. */
  static {
    allGPAs.add(new GradePointAverage(1L, "4.0"));
    allGPAs.add(new GradePointAverage(2L, "3.0 - 3.9"));
    allGPAs.add(new GradePointAverage(3L, "2.0 - 2.9"));
    allGPAs.add(new GradePointAverage(4L, "1.0 - 1.9"));

  }


}
