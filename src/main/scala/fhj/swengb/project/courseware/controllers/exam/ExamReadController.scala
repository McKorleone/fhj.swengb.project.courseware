package fhj.swengb.project.courseware.controllers.exam

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.Label
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.MutableExam

object ExamReadController extends BaseController {
  private var record: MutableExam = null
  def getRecord(): MutableExam = record
  def setRecord(r: MutableExam) = record = r
}

class ExamReadController extends BaseController {

  @FXML var idExam: Label = _
  @FXML var descriptionExam: Label = _
  @FXML var startExam: Label = _
  @FXML var endExam: Label = _
  @FXML var dateExam: Label = _
  @FXML var pointsExam: Label = _

  /**
    * @inheritdoc
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = ExamReadController.getRecord()

    idExam.setText(record.idProperty.getValue().toString)
    descriptionExam.setText(record.descriptionProperty.getValue())
    startExam.setText(record.startProperty.getValue())
    endExam.setText(record.endProperty.getValue())
    dateExam.setText(record.dateProperty.getValue())
    pointsExam.setText(record.pointsProperty.getValue().toString)
  }

  /**
    *  Buttons on-click functions
    */


}