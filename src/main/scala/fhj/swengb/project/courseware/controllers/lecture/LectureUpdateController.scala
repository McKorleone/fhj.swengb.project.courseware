package fhj.swengb.project.courseware.controllers.lecture

import java.net.URL
import java.time.LocalDate
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField, DatePicker}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.{MutableLecture, Lecture}

object LectureUpdateController extends BaseController {
  private var record: MutableLecture = null
  def getRecord(): MutableLecture = record
  def setRecord(r: MutableLecture) = record = r
}

class LectureUpdateController extends BaseController {

  @FXML var updateLectureButton: Button = _
  @FXML var cancelButton: Button = _
  @FXML var nameLecture: TextField = _
  @FXML var startLecture: TextField = _
  @FXML var endLecture: TextField = _
  @FXML var dateLecture: DatePicker = _
  @FXML var roomLecture: TextField = _
  @FXML var lecturerLecture: TextField = _
  @FXML var pointsLecture: TextField = _
  @FXML var invalidInput: Label = _
  @FXML var nameLectureError: Label = _
  @FXML var startLectureError: Label = _
  @FXML var endLectureError: Label = _
  @FXML var dateLectureError: Label = _
  @FXML var roomLectureError: Label = _
  @FXML var lecturerLectureError: Label = _
  @FXML var pointsLectureError: Label = _
  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = LectureUpdateController.getRecord()
    var lecture = Lecture.findOne(record.idProperty.getValue())

    nameLecture.setText(lecture.name)
    startLecture.setText(lecture.start)
    endLecture.setText(lecture.end)
    dateLecture.setValue(LocalDate.parse(lecture.date))
    roomLecture.setText(lecture.room.toString)
    lecturerLecture.setText(lecture.lecturer)
    pointsLecture.setText(lecture.points.toString)
  }

  /**
    *  Buttons on-click functions
    */

  def updateLectureButtonTrigger(): Unit = {
    val id: Int = LectureUpdateController.getRecord().idProperty.getValue.toInt
    var lecture: Lecture = Lecture.findOne(id)

    nameLectureError.getStyleClass().add("hidden")
    startLectureError.getStyleClass().add("hidden")
    endLectureError.getStyleClass().add("hidden")
    dateLectureError.getStyleClass().add("hidden")
    roomLectureError.getStyleClass().add("hidden")
    lecturerLectureError.getStyleClass().add("hidden")
    pointsLectureError.getStyleClass().add("hidden")

    lecture.attributes.put("name", nameLecture.getText())
    lecture.attributes.put("start", startLecture.getText())
    lecture.attributes.put("end", endLecture.getText())
    lecture.attributes.put("date", if (dateLecture.getValue() == null) "" else dateLecture.getValue().toString)
    lecture.attributes.put("room", roomLecture.getText())
    lecture.attributes.put("lecturer", lecturerLecture.getText())
    lecture.attributes.put("points", pointsLecture.getText())

    if (!lecture.update()) {
      if (lecture.hasError("name")) {
        nameLectureError.getStyleClass().clear()
        nameLectureError.getStyleClass().remove("hidden")
      }
      if (lecture.hasError("start")) {
        startLectureError.getStyleClass().clear()
        startLectureError.getStyleClass().remove("hidden")
      }
      if (lecture.hasError("end")) {
        endLectureError.getStyleClass().clear()
        endLectureError.getStyleClass().remove("hidden")
      }
      if (lecture.hasError("date")) {
        dateLectureError.getStyleClass().clear()
        dateLectureError.getStyleClass().remove("hidden")
      }
      if (lecture.hasError("room")) {
        roomLectureError.getStyleClass().clear()
        roomLectureError.getStyleClass().remove("hidden")
      }
      if (lecture.hasError("lecturer")) {
        lecturerLectureError.getStyleClass().clear()
        lecturerLectureError.getStyleClass().remove("hidden")
      }
      if (lecture.hasError("points")) {
        pointsLectureError.getStyleClass().clear()
        pointsLectureError.getStyleClass().remove("hidden")
      }
    } else {
      LectureUpdateController.getStage().hide()
      LectureListController.reloadLectureViewItems()
      LectureListController.getLectureView().getSelectionModel().select(0)
    }
  }

  def cancelButtonTrigger(): Unit = LectureUpdateController.getStage().hide
}