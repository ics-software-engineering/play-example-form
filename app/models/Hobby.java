package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import views.formdata.StudentFormData;

/**
 * Represent a student's hobbies.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Some methods to facilitate form display and manipulation (makeHobbyMap, etc.).
 * <li> Some fields and methods to "fake" a database of Hobbies.
 * </ul>
 */
public class Hobby {
  private long id;
  private String name;

  public Hobby(long id, String name) {
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
   * Create a map of hobby name -> boolean including all known hobbies
   * and setting the boolean to true if a given hobby is associated with the passed student.
   * @param student A student who may have zero or more hobbies, or null to create a hobby list
   * with all unchecked boxes.
   * @return A map of hobby names to booleans indicating the hobbies associated with the student.
   */
  public static Map<String, Boolean> makeHobbyMap(StudentFormData student) {
    Map<String, Boolean> hobbyMap = new HashMap<String, Boolean>();
    for (Hobby hobby : allHobbies) {
      hobbyMap.put(hobby.getName(), (student != null && student.hobbies.contains(hobby.getName())));
    }
    return hobbyMap;
  }

  /**
   * Return the Hobby instance in the database with name 'hobbyName' or null if not found.
   * @param hobbyName The hobby name.
   * @return The Hobby instance, or null.
   */
  public static Hobby findHobby(String hobbyName) {
    for (Hobby hobby : allHobbies) {
      if (hobbyName.equals(hobby.getName())) {
        return hobby;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return String.format("[Hobby %s]", this.name);
  }

  /** Fake a database of hobbies. */
  private static List<Hobby> allHobbies = new ArrayList<Hobby>();

  /** Instantiate the fake database of hobbies. */
  static {
    allHobbies.add(new Hobby(1L, "Surfing"));
    allHobbies.add(new Hobby(2L, "Biking"));
    allHobbies.add(new Hobby(3L, "Paddling"));
    allHobbies.add(new Hobby(4L, "Running"));
  }


}
