package fhj.swengb.project.courseware.controllers.lecture

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.Label
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.MutableLecture

object LectureReadController extends BaseController {
  private var record: MutableLecture = null
  def getRecord(): MutableLecture = record
  def setRecord(r: MutableLecture) = record = r
}

class LectureReadController extends BaseController {

  @FXML var idLecture: Label = _
  @FXML var nameLecture: Label = _
  @FXML var startLecture: Label = _
  @FXML var endLecture: Label = _
  @FXML var dateLecture: Label = _
  @FXML var roomLecture: Label = _
  @FXML var lecturerLecture: Label = _
  @FXML var pointsLecture: Label = _

  /**
    * @inheritdoc
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = LectureReadController.getRecord()

    idLecture.setText(record.idProperty.getValue().toString)
    nameLecture.setText(record.nameProperty.getValue())
    startLecture.setText(record.startProperty.getValue())
    endLecture.setText(record.endProperty.getValue())
    dateLecture.setText(record.dateProperty.getValue())
    roomLecture.setText(record.roomProperty.getValue().toString)
    lecturerLecture.setText(record.lecturerProperty.getValue())
    pointsLecture.setText(record.pointsProperty.getValue().toString)
  }

  /**
    *  Buttons on-click functions
    */


}