package fhj.swengb.project.courseware.controllers.exam

import java.net.URL
import java.time.LocalDate
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField, DatePicker}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.{MutableExam, Exam}

object ExamUpdateController extends BaseController {
  private var record: MutableExam = null
  def getRecord(): MutableExam = record
  def setRecord(r: MutableExam) = record = r
}

class ExamUpdateController extends BaseController {

  @FXML var updateExamButton: Button = _
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
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = ExamUpdateController.getRecord()
    var exam = Exam.findOne(record.idProperty.getValue())

    descriptionExam.setText(exam.description)
    startExam.setText(exam.start)
    endExam.setText(exam.end)
    dateExam.setValue(LocalDate.parse(exam.date))
    pointsExam.setText(exam.points.toString)
  }

  /**
    *  Buttons on-click functions
    */

  def updateExamButtonTrigger(): Unit = {
    val id: Int = ExamUpdateController.getRecord().idProperty.getValue.toInt
    var exam: Exam = Exam.findOne(id)

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

    if (!exam.update()) {
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
      ExamUpdateController.getStage().hide()
      ExamListController.reloadExamViewItems()
      ExamListController.getExamView().getSelectionModel().select(0)
    }
  }

  def cancelButtonTrigger(): Unit = ExamUpdateController.getStage().hide
}