package fhj.swengb.project.courseware.controllers.course

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField, DatePicker}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.{Course}

object CourseCreateController extends BaseController {}

class CourseCreateController extends BaseController {

  @FXML var createCourseButton: Button = _
  @FXML var cancelButton: Button = _
  @FXML var descriptionCourse: TextField = _
  @FXML var semesterCourse: TextField = _
  @FXML var startCourse: DatePicker = _
  @FXML var endCourse: DatePicker = _
  @FXML var pointsCourse: TextField = _
  @FXML var invalidInput: Label = _
  @FXML var descriptionCourseError: Label = _
  @FXML var semesterCourseError: Label = _
  @FXML var startCourseError: Label = _
  @FXML var endCourseError: Label = _
  @FXML var pointsCourseError: Label = _
  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {}

  /**
    *  Buttons on-click functions
    */

  def createCourseButtonTrigger(): Unit = {
    var course: Course = new Course

    descriptionCourseError.getStyleClass().add("hidden")
    semesterCourseError.getStyleClass().add("hidden")
    startCourseError.getStyleClass().add("hidden")
    endCourseError.getStyleClass().add("hidden")
    pointsCourseError.getStyleClass().add("hidden")

    course.attributes.put("description", descriptionCourse.getText())
    course.attributes.put("semester", semesterCourse.getText())
    course.attributes.put("start", if (startCourse.getValue() == null) "" else startCourse.getValue().toString)
    course.attributes.put("end", if (endCourse.getValue() == null) "" else endCourse.getValue().toString)
    course.attributes.put("points", pointsCourse.getText())

    if (!course.create()) {
      if (course.hasError("description")) {
        descriptionCourseError.getStyleClass().clear()
        descriptionCourseError.getStyleClass().remove("hidden")
      }
      if (course.hasError("semester")) {
        semesterCourseError.getStyleClass().clear()
        semesterCourseError.getStyleClass().remove("hidden")
      }
      if (course.hasError("start")) {
        startCourseError.getStyleClass().clear()
        startCourseError.getStyleClass().remove("hidden")
      }
      if (course.hasError("end")) {
        endCourseError.getStyleClass().clear()
        endCourseError.getStyleClass().remove("hidden")
      }
      if (course.hasError("points")) {
        pointsCourseError.getStyleClass().clear()
        pointsCourseError.getStyleClass().remove("hidden")
      }
    } else {
      CourseCreateController.getStage().hide()
      CourseListController.reloadCourseViewItems()
    }
  }

  def cancelButtonTrigger(): Unit = CourseCreateController.getStage().hide()
}