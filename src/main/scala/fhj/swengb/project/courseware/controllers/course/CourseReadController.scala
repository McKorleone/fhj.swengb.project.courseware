package fhj.swengb.project.courseware.controllers.course

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField, DatePicker}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.{MutableCourse, Course}

object CourseReadController extends BaseController {
  private var record: MutableCourse = null
  def getRecord(): MutableCourse = record
  def setRecord(r: MutableCourse) = record = r
}

class CourseReadController extends BaseController {

  @FXML var idCourse: Label = _
  @FXML var descriptionCourse: Label = _
  @FXML var semesterCourse: Label = _
  @FXML var startCourse: Label = _
  @FXML var endCourse: Label = _
  @FXML var pointsCourse: Label = _

  /**
    * @inheritdoc
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = CourseReadController.getRecord()

    idCourse.setText(record.idProperty.getValue().toString)
    descriptionCourse.setText(record.descriptionProperty.getValue())
    semesterCourse.setText(record.semesterProperty.getValue().toString)
    startCourse.setText(record.startProperty.getValue())
    endCourse.setText(record.endProperty.getValue())
    pointsCourse.setText(record.pointsProperty.getValue().toString)
  }

  /**
    *  Buttons on-click functions
    */


}