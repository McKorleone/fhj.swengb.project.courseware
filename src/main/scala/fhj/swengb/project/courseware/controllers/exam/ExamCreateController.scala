package fhj.swengb.project.courseware.controllers.exam

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField, DatePicker}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.Exam

object ExamCreateController extends BaseController {}

class ExamCreateController extends BaseController {

  @FXML var createExamButton: Button = _
  @FXML var cancelButton: Button = _
  @FXML var descriptionExam: TextField = _
  @FXML var startExam: TextField = _
  @FXML var endExam: TextField = _
  @FXML var dateExam: DatePicker = _
  @FXML var pointsExam: TextField = _
  @FXML var invalidInput: Label = _
  @FXML var descriptionExamError: Label = _
  @FXML var startExamError: Label = _
  @FXML var endExamError: Label = _
  @FXML var dateExamError: Label = _
  @FXML var pointsExamError: Label = _
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

  def createExamButtonTrigger(): Unit = {
    var exam: Exam = new Exam

    descriptionExamError.getStyleClass().add("hidden")
    startExamError.getStyleClass().add("hidden")
    endExamError.getStyleClass().add("hidden")
    dateExamError.getStyleClass().add("hidden")
    pointsExamError.getStyleClass().add("hidden")

    exam.attributes.put("description", descriptionExam.getText())
    exam.attributes.put("start", startExam.getText())
    exam.attributes.put("end", endExam.getText())
    exam.attributes.put("date", if (dateExam.getValue() == null) "" else dateExam.getValue().toString)
    exam.attributes.put("points", pointsExam.getText())

    if (!exam.create()) {
      if (exam.hasError("description")) {
        descriptionExamError.getStyleClass().clear()
        descriptionExamError.getStyleClass().remove("hidden")
      }
      if (exam.hasError("start")) {
        startExamError.getStyleClass().clear()
        startExamError.getStyleClass().remove("hidden")
      }
      if (exam.hasError("end")) {
        endExamError.getStyleClass().clear()
        endExamError.getStyleClass().remove("hidden")
      }
      if (exam.hasError("date")) {
        dateExamError.getStyleClass().clear()
        dateExamError.getStyleClass().remove("hidden")
      }
      if (exam.hasError("points")) {
        pointsExamError.getStyleClass().clear()
        pointsExamError.getStyleClass().remove("hidden")
      }
    } else {
      ExamCreateController.getStage().hide()
      ExamListController.reloadExamViewItems()
    }
  }

  def cancelButtonTrigger(): Unit = ExamCreateController.getStage().hide()
}