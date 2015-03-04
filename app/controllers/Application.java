package controllers;

import models.GradeLevel;
import models.GradePointAverage;
import models.Hobby;
import models.Major;
import models.Student;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.formdata.StudentFormData;
import views.html.Index;

/**
 * The controller for the single page of this application.
 *
 * @author Philip Johnson
 */
public class Application extends Controller {

  /**
   * Returns the page where the form is filled by the Student whose id is passed, or an empty form
   * if the id is 0.
   * @param id The id of the Student whose data is to be shown.  0 if an empty form is to be shown.
   * @return The page containing the form and data.
   */
  public static Result getIndex(long id) {
    StudentFormData studentData = (id == 0) ? new StudentFormData() : models.Student.makeStudentFormData(id);
    Form<StudentFormData> formData = Form.form(StudentFormData.class).fill(studentData);
    return ok(Index.render(
      formData,
      Hobby.makeHobbyMap(studentData),
      GradeLevel.getNameList(),
      GradePointAverage.makeGPAMap(studentData),
      Major.makeMajorMap(studentData)
    ));
  }

  /**
   * Process a form submission.
   * First we bind the HTTP POST data to an instance of StudentFormData.
   * The binding process will invoke the StudentFormData.validate() method.
   * If errors are found, re-render the page, displaying the error data. 
   * If errors not found, render the page with the good data. 
   * @return The index page with the results of validation. 
   */
  public static Result postIndex() {

    // Get the submitted form data from the request object, and run validation.
    Form<StudentFormData> formData = Form.form(StudentFormData.class).bindFromRequest();

    if (formData.hasErrors()) {
      // Don't call formData.get() when there are errors, pass 'null' to helpers instead. 
      flash("error", "Please correct errors above.");
      return badRequest(Index.render(formData,
        Hobby.makeHobbyMap(null), 
        GradeLevel.getNameList(),
        GradePointAverage.makeGPAMap(null), 
        Major.makeMajorMap(null) 
      ));
    }
    else {
      // Convert the formData into a Student model instance.
      Student student = Student.makeInstance(formData.get());
      flash("success", "Student instance created/edited: " + student);
      return ok(Index.render(formData,
        Hobby.makeHobbyMap(formData.get()),
        GradeLevel.getNameList(),
        GradePointAverage.makeGPAMap(formData.get()),
        Major.makeMajorMap(formData.get())
      ));
    }
  }
}
