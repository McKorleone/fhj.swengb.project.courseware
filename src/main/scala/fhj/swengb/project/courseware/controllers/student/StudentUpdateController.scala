package fhj.swengb.project.courseware.controllers.student

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.{MutableStudent, Student}

object StudentUpdateController extends BaseController {
  private var record: MutableStudent = null
  def getRecord(): MutableStudent = record
  def setRecord(r: MutableStudent) = record = r
}

class StudentUpdateController extends BaseController {

  @FXML var updateStudentButton: Button = _
  @FXML var cancelButton: Button = _
  @FXML var firstNameStudent: TextField = _
  @FXML var lastNameStudent: TextField = _
  @FXML var groupStudent: TextField = _
  @FXML var pointsStudent: TextField = _
  @FXML var percentageStudent: TextField = _
  @FXML var gradeStudent: TextField = _
  @FXML var invalidInput: Label = _
  @FXML var firstNameStudentError: Label = _
  @FXML var lastNameStudentError: Label = _
  @FXML var groupStudentError: Label = _
  @FXML var pointsStudentError: Label = _
  @FXML var percentageStudentError: Label = _
  @FXML var gradeStudentError: Label = _
  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = StudentUpdateController.getRecord()
    var student = Student.findOne(record.idProperty.getValue())

    firstNameStudent.setText(student.first_name)
    lastNameStudent.setText(student.last_name)
    groupStudent.setText(student.group.toString)
    pointsStudent.setText(student.points.toString)
    percentageStudent.setText(student.percentage.toString)
    gradeStudent.setText(student.grade.toString)
  }

  /**
    *  Buttons on-click functions
    */

  def updateStudentButtonTrigger(): Unit = {
    val id: Int = StudentUpdateController.getRecord().idProperty.getValue.toInt
    var student: Student = Student.findOne(id)

    firstNameStudentError.getStyleClass().add("hidden")
    lastNameStudentError.getStyleClass().add("hidden")
    groupStudentError.getStyleClass().add("hidden")
    pointsStudentError.getStyleClass().add("hidden")
    percentageStudentError.getStyleClass().add("hidden")
    gradeStudentError.getStyleClass().add("hidden")

    student.attributes.put("first_name", firstNameStudent.getText())
    student.attributes.put("last_name", lastNameStudent.getText())
    student.attributes.put("group", groupStudent.getText())
    student.attributes.put("points", pointsStudent.getText())
    student.attributes.put("percentage", percentageStudent.getText())
    student.attributes.put("grade", gradeStudent.getText())

    if (!student.update()) {
      if (student.hasError("first_name")) {
        firstNameStudentError.getStyleClass().clear()
        firstNameStudentError.getStyleClass().remove("hidden")
      }
      if (student.hasError("last_name")) {
        lastNameStudentError.getStyleClass().clear()
        lastNameStudentError.getStyleClass().remove("hidden")
      }
      if (student.hasError("group")) {
        groupStudentError.getStyleClass().clear()
        groupStudentError.getStyleClass().remove("hidden")
      }
      if (student.hasError("points")) {
        pointsStudentError.getStyleClass().clear()
        pointsStudentError.getStyleClass().remove("hidden")
      }
      if (student.hasError("percentage")) {
        percentageStudentError.getStyleClass().clear()
        percentageStudentError.getStyleClass().remove("hidden")
      }
      if (student.hasError("grade")) {
        gradeStudentError.getStyleClass().clear()
        gradeStudentError.getStyleClass().remove("hidden")
      }
    } else {
      StudentUpdateController.getStage().hide()
      StudentListController.reloadStudentViewItems()
      StudentListController.getStudentView().getSelectionModel().select(0)
    }
  }

  def cancelButtonTrigger(): Unit = StudentUpdateController.getStage().hide
}