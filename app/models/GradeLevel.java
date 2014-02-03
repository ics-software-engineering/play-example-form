package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represent a student grade level.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Some methods to facilitate form display and manipulation (getNameList, etc.).
 * <li> Some fields and methods to "fake" a database of GradeLevels.
 * </ul>
 */
public class GradeLevel {
  private long id;
  private String name;

  /**
   * Create a new Grade Level.
   * @param id The id.
   * @param name The name of the grade.
   */
  public GradeLevel(long id, String name) {
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
   * Provide a list of names for use in form display.
   * @return A list of level names in sorted order.
   */
  public static List<String> getNameList() {
    String[] nameArray = {"Freshman", "Sophomore", "Junior", "Senior", "Other"};
    return Arrays.asList(nameArray);
  }

  /**
   * Return the GradeLevel instance in the database with name 'levelName' or null if not found.
   * @param levelName The Level name.
   * @return The GradeLevel instance, or null if not found.
   */
  public static GradeLevel findLevel(String levelName) {
    for (GradeLevel level : allLevels) {
      if (levelName.equals(level.getName())) {
        return level;
      }
    }
    return null;
  }

  /**
   * Provide a default grade level for use in form display.
   * @return The default grade level.
   */
  public static GradeLevel getDefaultLevel() {
    return findLevel("Freshman");
  }

  @Override
  public String toString() {
    return String.format("[GradeLevel %s]", this.name);
  }

  /** Fake a database of Grade Levels. */
  private static List<GradeLevel> allLevels = new ArrayList<GradeLevel>();

  /** Instantiate the fake database. */
  static {
    allLevels.add(new GradeLevel(1L, "Freshman"));
    allLevels.add(new GradeLevel(2L, "Sophomore"));
    allLevels.add(new GradeLevel(3L, "Junior"));
    allLevels.add(new GradeLevel(4L, "Senior"));
    allLevels.add(new GradeLevel(5L, "Other"));
  }


}
