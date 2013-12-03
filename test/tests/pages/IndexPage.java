package tests.pages;

import static org.fluentlenium.core.filter.FilterConstructor.withId;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Implements the page object pattern for the index page of this application. 
 * See: https://github.com/FluentLenium/FluentLenium#what-is-fluentlenium-
 * And: http://ics-software-engineering.github.io/play-example-fluentlenium/
 */
public class IndexPage extends FluentPage {
  private String url;

  /**
   * Construct the page. Note that you must always pass a studentID.  
   * @param webDriver The driver.
   * @param port The port.
   * @param studentID The ID. Use 0 to get a blank form.
   */
  public IndexPage(WebDriver webDriver, int port, int studentID) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/?id=" + studentID;
  }
  
  @Override
  public String getUrl() {
    return this.url;
  }

  @Override
  public void isAt() {
    assertThat(title()).isEqualTo("play-example-form");
  }
  
  public void setName(String name) {
    fill("#name").with(name);
  }
  
  public void setPassword(String password) {
    fill("#password").with(password);
  }
  
  public void selectHobby(String hobby) {
    find("div", withId("hobbies")).find("input", withId(hobby)).click();
  }

  public void selectGradeLevel(String level) {
    find("div", withId("levels")).find("input", withId(level)).click();
  }
  
  public void selectGPA(String gpa) {
    find("select", withId("gpa")).find("option", withId(gpa)).click();
  }

  public void selectMajor(String major) {
    find("select", withId("majors")).find("option", withId(major)).click();
  }
  
  public void submit() {
    submit("#submit");
  }
  
  public void cancel() {
    find("#cancel").click();
  }
  
  public boolean hasSuccessMessage() {
    return findFirst("div", withId("success-message")) != null;
  }
  
  public boolean hasErrorMessage() {
    return findFirst("div", withId("error-message")) != null;
  }
}
