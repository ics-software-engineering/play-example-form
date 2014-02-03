package views.formdata;

import models.GradeLevel;
import models.GradePointAverage;
import models.Hobby;
import models.Major;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Backing class for the Student data form.
 * Requirements:
 * <ul>
 * <li> All fields are public, 
 * <li> All fields are of type String or List[String].
 * <li> A public no-arg constructor.
 * <li> A validate() method that returns null or a List[ValidationError].
 * </ul>
 */
public class StudentFormData {

  public String name = "";
  public String password = "";
  public List<String> hobbies = new ArrayList<String>(); 
  public String level = "";
  public String gpa = "";
  public List<String> majors = new ArrayList<String>(); 

  /** Required for form instantiation. */
  public StudentFormData() {
  }

  /**
   * Creates an initialized form instance. Assumes the passed data is valid. 
   * @param name The name.
   * @param password The password.
   * @param level The level.
   * @param gpa The GPA.
   * @param hobbies The hobbies.
   * @param majors The majors. 
   */
  public StudentFormData(String name, String password, GradeLevel level, GradePointAverage gpa, List<Hobby> hobbies, List<Major> majors) {
    this.name = name;
    this.password = password;
    this.level = level.getName();
    this.gpa = gpa.getName();
    for(Hobby hobby : hobbies) {
      this.hobbies.add(hobby.getName());
    }
    for(Major major : majors) {
      this.majors.add(major.getName());
    }
  }

  /**
   * Validates Form<StudentFormData>.
   * Called automatically in the controller by bindFromRequest().
   * 
   * Validation checks include:
   * <ul>
   * <li> Name must be non-empty.
   * <li> Password must be at least five characters.
   * <li> Hobbies (plural) are optional, but if specified, must exist in database.
   * <li> Grade Level is required and must exist in database.
   * <li> GPA is required and must exist in database.
   * <li> Majors (plural) are optional, but if specified, must exist in database.
   * </ul>
   *
   * @return Null if valid, or a List[ValidationError] if problems found.
   */
  public List<ValidationError> validate() {

    List<ValidationError> errors = new ArrayList<ValidationError>();

    if (name == null || name.length() == 0) {
      errors.add(new ValidationError("name", "No name was given."));
    }

    if (password == null || password.length() == 0) {
      errors.add(new ValidationError("password", "No password was given."));
    } else if (password.length() < 5) {
      errors.add(new ValidationError("password", "Given password is less than five characters."));
    }

    // Hobbies are optional, but if supplied must exist in database.
    if (hobbies.size() > 0) {
      for (String hobby : hobbies) {
        if (Hobby.findHobby(hobby) == null) {
          errors.add(new ValidationError("hobbies", "Unknown hobby: " + hobby + "."));
        }
      }
    }

    // Grade Level is required and must exist in database.
    if (level == null || level.length() == 0) {
      errors.add(new ValidationError("level", "No grade level was given."));
    } else if (GradeLevel.findLevel(level) == null) {
      errors.add(new ValidationError("level", "Invalid grade level: " + level + "."));
    }

    // GPA is required and must exist in database.
    if (gpa == null || gpa.length() == 0) {
      errors.add(new ValidationError("gpa", "No gpa was given."));
    } else if (GradePointAverage.findGPA(gpa) == null) {
      errors.add(new ValidationError("gpa", "Invalid GPA: " + gpa + "."));
    }

    // Majors are optional, but if supplied must exist in database.
    if (majors.size() > 0) {
      for (String major : majors) {
        if (Major.findMajor(major) == null) {
          errors.add(new ValidationError("majors", "Unknown Major: " + major + "."));
        }
      }
    }

    if(errors.size() > 0)
      return errors;

    return null;
  }
}
