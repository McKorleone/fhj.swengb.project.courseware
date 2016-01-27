package fhj.swengb.project.courseware.controllers.homework

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField, DatePicker}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.Homework

object HomeworkCreateController extends BaseController {}

class HomeworkCreateController extends BaseController {

  @FXML var createHomeworkButton: Button = _
  @FXML var cancelButton: Button = _
  @FXML var descriptionHomework: TextField = _
  @FXML var dateHomework: DatePicker = _
  @FXML var deadlineHomework: DatePicker = _
  @FXML var pointsHomework: TextField = _
  @FXML var invalidInput: Label = _
  @FXML var descriptionHomeworkError: Label = _
  @FXML var dateHomeworkError: Label = _
  @FXML var deadlineHomeworkError: Label = _
  @FXML var pointsHomeworkError: Label = _
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

  def createHomeworkButtonTrigger(): Unit = {
    var homework: Homework = new Homework

    descriptionHomeworkError.getStyleClass().add("hidden")
    dateHomeworkError.getStyleClass().add("hidden")
    deadlineHomeworkError.getStyleClass().add("hidden")
    pointsHomeworkError.getStyleClass().add("hidden")

    homework.attributes.put("description", descriptionHomework.getText())
    homework.attributes.put("date", if (dateHomework.getValue() == null) "" else dateHomework.getValue().toString())
    homework.attributes.put("deadline", if (deadlineHomework.getValue() == null) "" else deadlineHomework.getValue().toString())
    homework.attributes.put("points", pointsHomework.getText())

    if (!homework.create()) {
      if (homework.hasError("description")) {
        descriptionHomeworkError.getStyleClass().clear()
        descriptionHomeworkError.getStyleClass().remove("hidden")
      }
      if (homework.hasError("date")) {
        dateHomeworkError.getStyleClass().clear()
        dateHomeworkError.getStyleClass().remove("hidden")
      }
      if (homework.hasError("deadline")) {
        deadlineHomeworkError.getStyleClass().clear()
        deadlineHomeworkError.getStyleClass().remove("hidden")
      }
      if (homework.hasError("points")) {
        pointsHomeworkError.getStyleClass().clear()
        pointsHomeworkError.getStyleClass().remove("hidden")
      }
    } else {
      HomeworkCreateController.getStage().hide()
      HomeworkListController.reloadHomeworkViewItems()
    }
  }

  def cancelButtonTrigger(): Unit = HomeworkCreateController.getStage().hide()
}