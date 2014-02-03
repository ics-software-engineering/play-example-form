Overview
========

![screenshot](https://raw.github.com/ics-software-engineering/play-example-form/master/tutorial/play-example-form-homepage.png)

This application provides an example of form processing with the following features:

  * Play Version 2.2
  * [WebJars](http://www.webjars.org/) to download dependencies.
  * [Twitter Bootstrap 3.0.0](http://getbootstrap.com/).
  * Individual Twitter Bootstrap [helper templates](https://github.com/ics-software-engineering/play-example-form/tree/master/app/views/bootstrap3) for each form control.
  * Separation of [form backing classes](https://github.com/ics-software-engineering/play-example-form/tree/master/app/views/formdata) from [model classes](https://github.com/ics-software-engineering/play-example-form/tree/master/app/models).
  * Validation done with [validate()](https://github.com/ics-software-engineering/play-example-form/blob/master/app/views/formdata/StudentFormData.java#L57-123), not annotations.
  * Testing with [Fluentlenium](https://github.com/FluentLenium/FluentLenium).
    
The design of this example differs in two significant ways from the traditional Play form processing examples. 

  1. **Distinct model and form classes.**  Most examples of form processing in Play "overload" the 
     model classes to serve two tasks:  (1) specify the database schema structure; and 
     (2) provide the "backing class" for forms.  Requiring a single class to perform these two tasks
     appears to work well only when the models and forms are both simple and similar in structure. In this example system, the
     [views.formdata package](https://github.com/ics-software-engineering/play-example-form/tree/master/app/views/formdata) provides 
     classes for form processing, and the [models package](https://github.com/ics-software-engineering/play-example-form/tree/master/app/models) provides
     classes for database schemas. Since Play requires the backing classes for forms to have public fields,
     this separation means that model classes can have private fields, avoiding [well documented problems](http://www.manning-sandbox.com/thread.jspa?messageID=143570&#143570). 

  2. **Explicit field constructors for Twitter Bootstrap 3.x.**  The canonical recommendation by Play developers for users of 
     Twitter Bootstrap is to create a single "implicit" field constructor.  The problem with this recommendation
     is that a single implicit field constructor cannot satisfy all of Twitter Bootstrap's layout
     requirements for form controls (for example, multiple checkboxes). This example illustrates
     a more general solution in which normal (i.e. "explicit") scala templates (i.e. field constructors) are defined in the 
     [views.bootstrap3 package](https://github.com/ics-software-engineering/play-example-form/tree/master/app/views/bootstrap3) for each of the Twitter Bootstrap controls. IMHO, the 
     code is significantly easier to understand and debug for Java-based Play framework users.  

Steps to understanding the system
---------------------------------

**Run the application.**

Begin by downloading the code, invoking "play run" in your shell, then retrieving http://localhost:9000 
to display the single form illustrated at the top of this page. The form displays the fields
associated with a "Student":  Name, Password, Hobbies, Level, GPA, and Majors.  Some of these
are required, some are optional. Try filling out the form in both valid and invalid ways and
pressing Submit to see what happens.

When you submit a valid version of the form, the system will redisplay the form with exactly the 
same data that you entered, and also show a representation of the Student model instance
created from the form values.

**Run the tests.**

Next, type "control-D" in your shell to bring down the development server, and then "play test" 
to invoke the test cases. You should get output similar to the following:

    [~/play-example-form]-> play test
    [info] Loading project definition from /Users/johnson/projecthosting/github/play-example-form/project
    [info] Set current project to play-example-form (in build file:/Users/johnson/projecthosting/github/play-example-form/)
    [info] tests.ViewTest
    [info] + tests.ViewTest.testIndexPageRetrieval
    [info] + tests.ViewTest.testIndexPageEmptySubmission
    [info] + tests.ViewTest.testIndexPageValidSubmission
    [info] + tests.ViewTest.testIndexPageFormFilledSubmission
    [info] 
    [info] 
    [info] Total for test tests.ViewTest
    [info] Finished in 0.015 seconds
    [info] 4 tests, 0 failures, 0 errors
    [info] Passed: : Total 4, Failed 0, Errors 0, Passed 4, Skipped 0
    [success] Total time: 8 s, completed Aug 16, 2013 10:05:33 AM

We'll come back to this later, but this step verifies that tests run correctly in your environment.

**Review the controllers.**

Now review the controller class. [Application](https://github.com/ics-software-engineering/play-example-form/blob/master/app/controllers/Application.java)
has just two methods: getIndex() which displays the form in the index page and postIndex() that processes a form submission
from the index page. See the [routes](https://github.com/ics-software-engineering/play-example-form/blob/master/conf/routes) file for how this is wired up.

The getIndex method takes a Student ID as a parameter. If the value is 0, then an empty form is
displayed, otherwise the form is displayed pre-filled with the data associated with the Student ID.
For example, you can retrieve the data for the student with ID 1 using: http://localhost:9000/?id=1.
The system [instantiates a couple of students on startup](https://github.com/ics-software-engineering/play-example-form/blob/master/app/models/Student.java#L191-203). 

By looking at the controller, you can see the basic approach for either form display (HTTP GET) or 
form submission (HTTP POST):
  
  * An instance of StudentFormData is passed to the templates for rendering. This class has public
    fields as required by Play, and they are all String or List[String] because binding only works on strings.
    
  * Other component entities (Hobby, GradeLevel, GradePointAverage, Major) provide helper methods
    to support display of their values as strings along with the student's current value(s) for
    those components.
    
  * The [Student.makeInstance](https://github.com/ics-software-engineering/play-example-form/blob/master/app/models/Student.java#L165-185) and [Student.makeStudentFormData](https://github.com/ics-software-engineering/play-example-form/blob/master/app/models/Student.java#L150-162)
    methods provide conversion between the form data and model representations of a Student.

**Review the models.**

Skim through the [models package](https://github.com/ics-software-engineering/play-example-form/tree/master/app/models). 
There should be no surprises; it parallels the form pretty closely.  Some things to note:

  * A Student entity contains non-primitive, complex components such as a list of Hobby entities and a list of Major entities.
  
  * The models have private fields and getters/setters. (Sorry, I'm old school that way.)     

**Review the views.**

The [views package](https://github.com/ics-software-engineering/play-example-form/tree/master/app/views) 
is where things get most interesting.   The [main](https://github.com/ics-software-engineering/play-example-form/blob/master/app/views/main.scala.html)
and [index](https://github.com/ics-software-engineering/play-example-form/blob/master/app/views/index.scala.html)
templates are pretty much what you'd expect. 

Note that the main template shows how to import JQuery in case you want to use Bootstrap Javascript components.

The second thing to review is the [views.bootstrap3](https://github.com/ics-software-engineering/play-example-form/tree/master/app/views/bootstrap3)
subpackage, containing Bootstrap 3 Scala templates for various form controls. Kudos to [Jason
Pearson](https://github.com/kaeawc) to writing these templates and making other helpful changes.

Finally, the [views.formdata](https://github.com/ics-software-engineering/play-example-form/tree/master/app/views/formdata)
subpackage contains the single backing class ([StudentFormData](https://github.com/ics-software-engineering/play-example-form/blob/master/app/views/formdata/StudentFormData.java)) required for this application.
Note that the backing class consists of public fields containing the String data to be displayed/bound in the form,
as well as a validate() method that determines if the submitted form data is valid or not.

**Review the tests.**

Testing is pretty straightforward, uses [Fluentlenium](https://github.com/FluentLenium/FluentLenium#what-is-fluentlenium-), and implements the 
[page object pattern](https://github.com/FluentLenium/FluentLenium/wiki/Page-Object-Pattern).

Start by looking at [IndexPage](https://github.com/ics-software-engineering/play-example-form/blob/master/test/tests/pages/IndexPage.java).
This class implements a bunch of methods to fill out the form and check to see whether the 
displayed form contains a success or error message. Note that most of these methods depend upon
finding an HTML element with a specific ID, and so the Bootstrap Scala templates need to make
sure these ID fields are set correctly.

The actual test code is in [ViewTest](https://github.com/ics-software-engineering/play-example-form/blob/master/test/tests/ViewTest.java).
There are four tests: one that just checks that we can retrieve the page, one that tests that
submitting an empty form generates a validation error, one that submits a form filled out 
from a valid Student ID, and a final one that fills out a valid form manually by using the 
IndexPage methods. 

Getting tests to work exposes an unfortunate library versioning issue: [HTMLUnit](http://htmlunit.sourceforge.net/) requires 
a version of JQuery no later than 1.8.3, while recent versions of Twitter Bootstrap 
have a Maven dependency of JQuery 1.9.0.  [Build.scala](https://github.com/ics-software-engineering/play-example-form/blob/master/project/Build.scala#L17-19)
illustrates how to load a newer version of Bootstrap with an older, HTMLUnit-compliant version of JQuery.
Another solution is to use [PhantomJS](http://phantomjs.org/) rather than HTMLUnit; then you can
use current versions of JQuery. [This fork](https://github.com/buster84/play-example-form) shows how to use PhantomJS.
        
Issues
------

While this code works and is pretty easy to understand, there are at least two design problems with it
that I can see:

  * **Verbosity.**  It's kind of a drag to have two representations for a Student, one as a model and
    one as a backing class for forms.   I know that I presented this as a feature, but at the end
    of the day it's born of necessity.  Perhaps there exists an elegant way to implement composite entities
    (i.e. a Student that contains a List of Hobbies) in which display, binding, and validation
    can be done easily and understandably with a single class.
    
  * **Integrity.**  The code provides validation in the StudentFormData class, and certain
    methods (such as Student.makeInstance) must assume that they are being passed a valid
    StudentFormData instance.  That kind of assumption is worrisome, and annotation-based 
    constraints could avoid it.  Annotation-based constraints also offer the potential
    to simultaneously apply to both the database model and the form validation, which would be really
    nice.  As a first step, I played around for a while with [Custom Data Binding](http://www.playframework.com/documentation/2.0/JavaForms),
    but could not get it to work correctly for lists of Hobbies.
    
If you see ways to solve these problems, please feel free to fork this code and implement your changes. 

Screencast
----------

Click the image below to watch a 28 minute walkthrough of this system. 

*Note that the following screencast documents a previous version of this system which used Twitter Bootstrap 2.3.2 and Play 2.1.  While the templates in the bootstrap3 package
have been rewritten, the rest of the system remains unchanged.*

[<img src="https://raw.github.com/ics-software-engineering/play-example-form/master/tutorial/play-example-form-screencast.png" width="400">](http://www.youtube.com/watch?v=247H9NVpMME)

    
Acknowledgements
----------------

This example is a descendent of the original [play-form-kludge](https://github.com/philipmjohnson/play-form-kludge/tree/original)
and [Jason Pearson](https://github.com/kaeawc)'s [very helpful improvements](https://github.com/philipmjohnson/play-form-kludge).

    
