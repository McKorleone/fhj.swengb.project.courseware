package fhj.swengb.project.courseware.controllers.student

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.Label
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.MutableStudent

object StudentReadController extends BaseController {
  private var record: MutableStudent = null
  def getRecord(): MutableStudent = record
  def setRecord(r: MutableStudent) = record = r
}

class StudentReadController extends BaseController {

  @FXML var idStudent: Label = _
  @FXML var firstNameStudent: Label = _
  @FXML var lastNameStudent: Label = _
  @FXML var groupStudent: Label = _
  @FXML var pointsStudent: Label = _
  @FXML var percentageStudent: Label = _
  @FXML var gradeStudent: Label = _

  /**
    * @inheritdoc
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = StudentReadController.getRecord()

    idStudent.setText(record.idProperty.getValue().toString)
    firstNameStudent.setText(record.firstNameProperty.getValue())
    lastNameStudent.setText(record.lastNameProperty.getValue())
    groupStudent.setText(record.groupProperty.getValue().toString)
    pointsStudent.setText(record.pointsProperty.getValue().toString)
    percentageStudent.setText(record.percentageProperty.getValue().toString)
    gradeStudent.setText(record.gradeProperty.getValue().toString)
  }

  /**
    *  Buttons on-click functions
    */
}