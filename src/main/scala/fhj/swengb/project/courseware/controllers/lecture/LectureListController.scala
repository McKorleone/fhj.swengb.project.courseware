package fhj.swengb.project.courseware.controllers.lecture

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml._
import javafx.scene.control.{Button, Label, TableColumn, TableView}
import fhj.swengb.project.courseware.components.JfxUtils._
import fhj.swengb.project.courseware.components.{Helpers, BaseController}
import fhj.swengb.project.courseware.models.Lecture
import fhj.swengb.project.courseware.models.MutableLecture
import javafx.stage.FileChooser
import java.io._

object LectureListController extends BaseController {
  private var lectureView: TableView[MutableLecture] = null
  def setLectureView(cv: TableView[MutableLecture]) = lectureView = cv
  def getLectureView(): TableView[MutableLecture] = lectureView

  private var lectureDeleteButton: Button = null
  def setLectureDeleteButton(cb: Button) = lectureDeleteButton = cb
  def getLectureDeleteButton(): Button = lectureDeleteButton

  private var lectureUpdateButton: Button = null
  def setLectureUpdateButton(cb: Button) = lectureUpdateButton = cb
  def getLectureUpdateButton(): Button = lectureUpdateButton

  private var lectureReadButton: Button = null
  def setLectureReadButton(cb: Button) = lectureReadButton = cb
  def getLectureReadButton(): Button = lectureReadButton

  def getLectureViewSelectedItem(): MutableLecture = lectureView.getSelectionModel().getSelectedItem()

  /**
    * Reloads items to Lecture View and refreshes it
    */
  def reloadLectureViewItems() = {
    lectureView.setItems(mkObservableList(Lecture.findAll().map(MutableLecture(_))))
    if (lectureView.getItems().size > 0) {
      lectureDeleteButton.setDisable(false)
      lectureUpdateButton.setDisable(false)
      lectureReadButton.setDisable(false)
    } else {
      lectureDeleteButton.setDisable(true)
      lectureUpdateButton.setDisable(true)
      lectureReadButton.setDisable(true)
    }
    lectureView.getSelectionModel().select(0)
  }
}

class LectureListController extends BaseController {

  /**
    * Specify type for table column
    *
    * @tparam T Mutable Class
    */
  type LectureTC[T] = TableColumn[MutableLecture, T]

  /**
    * Provide a table column and a generator function for the value to put into the column.
    *
    * @tparam T the type which is contained in the property
    * @return
    */
  def initTableViewColumn[T]: (LectureTC[T], (MutableLecture) => Any) => Unit = initTableViewColumnCellValueFactory[MutableLecture, T]

  @FXML var lectureView: TableView[MutableLecture] = _
  @FXML var columnLectureId: LectureTC[Int] = _
  @FXML var columnLectureName: LectureTC[String] = _
  @FXML var columnLectureStart: LectureTC[String] = _
  @FXML var columnLectureEnd: LectureTC[String] = _
  @FXML var columnLectureDate: LectureTC[String] = _
  @FXML var columnLectureRoom: LectureTC[Int] = _
  @FXML var columnLectureLecturer: LectureTC[String] = _
  @FXML var columnLecturePoints: LectureTC[Double] = _
  @FXML var lectureQueryOutput: Label = _
  @FXML var lectureCreateButton: Button = _
  @FXML var lectureUpdateButton: Button = _
  @FXML var lectureDeleteButton: Button = _
  @FXML var lectureReadButton: Button = _
//  @FXML var lectureQuery1Button: Button = _
//  @FXML var lectureQuery2Button: Button = _

  val loaderCreatePath = "/fhj.swengb.project/lecture/create.fxml"
  val loaderUpdatePath = "/fhj.swengb.project/lecture/update.fxml"
  val loaderReadPath = "/fhj.swengb.project/lecture/read.fxml"

  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    LectureListController.setLectureView(lectureView)
    LectureListController.setLectureUpdateButton(lectureUpdateButton)
    LectureListController.setLectureDeleteButton(lectureDeleteButton)
    LectureListController.setLectureReadButton(lectureReadButton)
    LectureListController.reloadLectureViewItems()

    initTableViewColumn[Int](columnLectureId, _.idProperty)
    initTableViewColumn[String](columnLectureName, _.nameProperty)
    initTableViewColumn[String](columnLectureStart, _.startProperty)
    initTableViewColumn[String](columnLectureEnd, _.endProperty)
    initTableViewColumn[String](columnLectureDate, _.dateProperty)
    initTableViewColumn[Int](columnLectureRoom, _.roomProperty)
    initTableViewColumn[String](columnLectureLecturer, _.lecturerProperty)
    initTableViewColumn[Double](columnLecturePoints, _.pointsProperty)
  }

  /**
    *  Buttons on-click functions
    */

  def lectureCreateButtonTrigger(): Unit = openWindow(loaderCreatePath, cssFilePath, LectureCreateController)

  def lectureUpdateButtonTrigger(): Unit = {
    val record = LectureListController.getLectureViewSelectedItem()
    LectureUpdateController.setRecord(record)
    openWindow(loaderUpdatePath, cssFilePath, LectureUpdateController)
  }

  def lectureDeleteButtonTrigger(): Unit = {
    val id: Int = LectureListController.getLectureViewSelectedItem().idProperty.getValue().toInt
    var lecture: Lecture = Lecture.findOne(id)
    lecture.delete()
    LectureListController.reloadLectureViewItems()
  }

  def lectureReadButtonTrigger(): Unit = {
    val record = LectureListController.getLectureViewSelectedItem()
    LectureReadController.setRecord(record)
    openWindow(loaderReadPath, cssFilePath, LectureReadController)
  }

//  def lectureQuery1ButtonTrigger(): Unit = sys.exit()
//
//  def lectureQuery2ButtonTrigger(): Unit = sys.exit()

  def lectureExportButtonTrigger(): Unit = {
    val fileChooser: FileChooser = new FileChooser
    val path = fileChooser.showSaveDialog(LectureListController.getStage())
    if (path == null)
      return

    val file = new PrintWriter(new File(path.toString))

    file.write("Id;Name;Start Time;End Time;Room;Lecturer Name;Points\r\n");

    val lectures: List[Lecture] = Lecture.findAll()
    for(lecture <- lectures) {
      file.write(
        lecture.id + ";" +
        Helpers.prepareCsvString(lecture.name)+ ";" +
        lecture.start + ";" +
        lecture.end + ";" +
        lecture.date + ";" +
        lecture.room + ";" +
        Helpers.prepareCsvString(lecture.lecturer) + ";" +
        lecture.points +
        "\r\n")
    }

    file.close()
  }


}