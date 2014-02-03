package models;

import java.util.ArrayList;
import java.util.List;
import views.formdata.StudentFormData;

/**
 * Simple model class to represent students.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Methods to facilitate form display (makeStudentFormData).
 * <li> Some fields and methods to "fake" a database of Students, including valid and invalid.
 * </ul> 
 */
public class Student {
  private long id;
  private String name;
  private String password;
  private List<Hobby> hobbies = new ArrayList<Hobby>(); // Hobbies are optional.
  private GradeLevel level;
  private GradePointAverage gpa;
  private List<Major> majors = new ArrayList<Major>(); // Majors are optional.

  /** Model entities typically want to have a no-arg constructor. */
  public Student() {
  }

  public Student(long id, String name, String password, GradeLevel level, GradePointAverage gpa) {
    this.setId(id);
    this.name = name;
    this.password = password;
    this.level = level;
    this.gpa = gpa;
  }
  
  /**
   * @return the id
   */
  private long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  private void setId(long id) {
    this.id = id;
  }

  public boolean hasHobby(String hobbyName) {
    for (Hobby hobby : this.hobbies) {
      if (hobbyName.equals(hobby.getName()))
        return true;
    }
    return false;
  }

  public void addHobby(Hobby hobby) {
    this.hobbies.add(hobby);
  }

  public boolean hasMajor(String majorName) {
    for (Major major : this.getMajors()) {
      if (majorName.equals(major.getName()))
        return true;
    }
    return false;
  }

  public String toString() {
    return String.format("[Student name: '%s' Password: '%s' Hobbies: %s Grade Level: %s GPA: %s Majors: %s]", this.getName(),
        this.getPassword(), this.hobbies, this.level, this.gpa, this.getMajors());
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the level
   */
  public GradeLevel getLevel() {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(GradeLevel level) {
    this.level = level;
  }

  /**
   * @return the gpa
   */
  public GradePointAverage getGpa() {
    return gpa;
  }

  /**
   * @param gpa the gpa to set
   */
  public void setGpa(GradePointAverage gpa) {
    this.gpa = gpa;
  }

  /**
   * @return the majors
   */
  public List<Major> getMajors() {
    return majors;
  }

  /**
   * @param majors the majors to set
   */
  public void setMajors(List<Major> majors) {
    this.majors = majors;
  }

  public void addMajor(Major major) {
    this.majors.add(major);
  }

  /**
   * Return a StudentFormData instance constructed from a student instance.
   * @param id The ID of a student instance.
   * @return The StudentFormData instance, or throws a RuntimeException. 
   */
  public static StudentFormData makeStudentFormData(long id) {
    for (Student student : allStudents) {
      if (student.getId() == id) {
        return new StudentFormData(student.name, student.password, student.level, student.gpa, student.hobbies, student.majors);
      }
    }
    throw new RuntimeException("Couldn't find student");
  }
  

  /**
   * Returns a Student instance created from the form data.
   * Assumes that the formData has been validated.
   * The ID field is not assigned or managed in this application.
   * @param formData The student form data.
   * @return A student instance. 
   */
  public static Student makeInstance(StudentFormData formData) {
    Student student = new Student();
    student.name = formData.name;
    student.password = formData.password;
    for (String hobby : formData.hobbies) {
      student.hobbies.add(Hobby.findHobby(hobby));
    }
    student.level = GradeLevel.findLevel(formData.level);
    student.gpa = GradePointAverage.findGPA(formData.gpa);
    for (String major : formData.majors) {
      student.majors.add(Major.findMajor(major));
    }
    return student;
  }
  
  
  /** Fake a database of students. */
  private static List<Student> allStudents = new ArrayList<Student>();

  /** Populate the fake database with both valid and invalid students, just for tutorial purposes.*/
  static {
    // Valid student. No optional data supplied.
    allStudents.add(new Student(1L, "Joe Good", "mypassword", GradeLevel.findLevel("Freshman"), GradePointAverage.findGPA("4.0")));
    // Valid student with optional data.
    allStudents.add(new Student(2L, "Alice Good", "mypassword", GradeLevel.findLevel("Sophomore"), GradePointAverage.findGPA("4.0")));
    getById(2L).addHobby(Hobby.findHobby("Biking"));
    getById(2L).addHobby(Hobby.findHobby("Surfing"));
    getById(2L).addMajor(Major.findMajor("Chemistry"));
    getById(2L).addMajor(Major.findMajor("Physics"));
    // Invalid student. Password is too short.
    allStudents.add(new Student(3L, "Frank Bad", "pass", GradeLevel.findLevel("Freshman"), GradePointAverage.findGPA("4.0")));
  }
  
  /**
   * Find a student instance given the ID.
   * @param id The id of the student.
   * @return The Student instance, or throws a RuntimeException.
   */
  public static Student getById(long id) {
    for (Student student : allStudents) {
      if (student.getId() == id) {
        return student;
      }
    }
    throw new RuntimeException("Couldn't find student");
  }

}
