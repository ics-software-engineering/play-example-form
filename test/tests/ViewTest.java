package tests;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;
import tests.pages.IndexPage;


public class ViewTest {
  private final int testPort = 3333;
  
  /** Test simple retrieval of the index page. */
  @Test
  public void testIndexPageRetrieval() {
    running(testServer(testPort, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      @Override
      public void invoke(TestBrowser browser) {
        browser.maximizeWindow();
        IndexPage indexPage = new IndexPage(browser.getDriver(), testPort, 0);
        browser.goTo(indexPage);
        indexPage.isAt();
      }
    });
  }
  
  /** Test submission of an empty form. */
  @Test
  public void testIndexPageEmptySubmission() {
    running(testServer(testPort, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      @Override
      public void invoke(TestBrowser browser) {
        browser.maximizeWindow();
        IndexPage indexPage = new IndexPage(browser.getDriver(), testPort, 0);
        browser.goTo(indexPage);
        indexPage.isAt();
        indexPage.submit();
        assertThat(indexPage.hasErrorMessage()).isTrue();
      }
    });
  }
  
  /** Test submission of a valid form. */
  @Test
  public void testIndexPageValidSubmission() {
    running(testServer(testPort, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      @Override
      public void invoke(TestBrowser browser) {
        browser.maximizeWindow();
        IndexPage indexPage = new IndexPage(browser.getDriver(), testPort, 1);
        browser.goTo(indexPage);
        indexPage.isAt();
        indexPage.submit();
        assertThat(indexPage.hasSuccessMessage()).isTrue();
      }
    });
  }
  
  /** Test submission of a manually filled out form. */
  @Test
  public void testIndexPageFormFilledSubmission() {
    running(testServer(testPort, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      @Override
      public void invoke(TestBrowser browser) {
        browser.maximizeWindow();
        IndexPage indexPage = new IndexPage(browser.getDriver(), testPort, 0);
        browser.goTo(indexPage);
        indexPage.isAt();
        indexPage.setName("Ronald D. Moore");
        indexPage.setPassword("Battlestar Galactica");
        indexPage.selectHobby("Surfing");
        indexPage.selectHobby("Biking");
        indexPage.selectGradeLevel("Freshman");
        indexPage.selectGPA("4.0");
        indexPage.selectMajor("Physics");
        indexPage.selectMajor("Mathematics");
        indexPage.submit();
        //System.out.println(browser.pageSource());  // useful for debugging.
        assertThat(indexPage.hasSuccessMessage()).isTrue();
      }
    });
  }  

}
