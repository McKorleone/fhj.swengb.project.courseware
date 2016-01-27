package fhj.swengb.project.courseware.controllers.homework


import java.net.URL
import java.time.LocalDate
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField, DatePicker}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.{MutableHomework, Homework}

object HomeworkUpdateController extends BaseController {
  private var record: MutableHomework = null
  def getRecord(): MutableHomework = record
  def setRecord(r: MutableHomework) = record = r
}

class HomeworkUpdateController extends BaseController {

  @FXML var updateHomeworkButton: Button = _
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
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = HomeworkUpdateController.getRecord()
    var homework = Homework.findOne(record.nrProperty.getValue())

    descriptionHomework.setText(homework.description)
    dateHomework.setValue(LocalDate.parse(homework.date))
    deadlineHomework.setValue(LocalDate.parse(homework.deadline))
    pointsHomework.setText(homework.points.toString)
  }

  /**
    *  Buttons on-click functions
    */

  def updateHomeworkButtonTrigger(): Unit = {
    val nr: Int = HomeworkUpdateController.getRecord().nrProperty.getValue.toInt
    var homework: Homework = Homework.findOne(nr)

    descriptionHomeworkError.getStyleClass().add("hidden")
    dateHomeworkError.getStyleClass().add("hidden")
    deadlineHomeworkError.getStyleClass().add("hidden")
    pointsHomeworkError.getStyleClass().add("hidden")

    homework.attributes.put("description", descriptionHomework.getText())
    homework.attributes.put("date", if (dateHomework.getValue() == null) "" else dateHomework.getValue().toString)
    homework.attributes.put("deadline", if (deadlineHomework.getValue() == null) "" else deadlineHomework.getValue().toString)
    homework.attributes.put("points", pointsHomework.getText())

    if (!homework.update()) {
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
      HomeworkUpdateController.getStage().hide()
      HomeworkListController.reloadHomeworkViewItems()
      HomeworkListController.getHomeworkView().getSelectionModel().select(0)
    }
  }

  def cancelButtonTrigger(): Unit = HomeworkUpdateController.getStage().hide
}
