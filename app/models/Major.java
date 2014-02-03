package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import views.formdata.StudentFormData;

/**
 * Represent a student's majors.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Some methods to facilitate form display and manipulation (makeMajorMap, etc.).
 * <li> Some fields and methods to "fake" a database of Majors.
 * </ul>
 */
public class Major {
  private long id;
  private String name;

  public Major(long id, String name) {
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
   * Create a map of Major name -> boolean indicating all defined majors
   * and setting the boolean to true if the given Major is associated with the passed student.
   * @param student A student who may have zero or more Majors, or null to create a Major list
   * with all unchecked boxes.
   * @return A map of Major names to booleans indicating the majors associated with the student.
   */
  public static Map<String, Boolean> makeMajorMap(StudentFormData student) {
    Map<String, Boolean> majorMap = new HashMap<String, Boolean>();
    for (Major major : allMajors) {
      majorMap.put(major.getName(),  (student == null) ? false : student.majors.contains(major.getName()));
    }
    return majorMap;
  }

  /**
   * Return the Major instance in the database with name 'MajorName' or null if not found.
   * @param majorName The Major name.
   * @return The Major instance, or null.
   */
  public static Major findMajor(String majorName) {
    for (Major major : allMajors) {
      if (majorName.equals(major.getName())) {
        return major;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return String.format("[Major %s]", this.name);
  }

  /** Fake a database of majors. */
  private static List<Major> allMajors = new ArrayList<Major>();

  /** Instantiate the fake database of Majors. */
  static {
    allMajors.add(new Major(1L, "Chemistry"));
    allMajors.add(new Major(2L, "Biology"));
    allMajors.add(new Major(3L, "Physics"));
    allMajors.add(new Major(4L, "Mathematics"));
  }


}
