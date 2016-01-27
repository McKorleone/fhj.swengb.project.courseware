package fhj.swengb.project.courseware.controllers.course

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml._
import javafx.scene.control.{Button, Label, TableColumn, TableView}
import fhj.swengb.project.courseware.components.JfxUtils._
import fhj.swengb.project.courseware.components.{Helpers, BaseController}
import fhj.swengb.project.courseware.models.Course
import fhj.swengb.project.courseware.models.MutableCourse
import javafx.stage.FileChooser
import java.io._

object CourseListController extends BaseController {
  private var courseView: TableView[MutableCourse] = null
  def setCourseView(cv: TableView[MutableCourse]) = courseView = cv
  def getCourseView(): TableView[MutableCourse] = courseView

  private var courseDeleteButton: Button = null
  def setCourseDeleteButton(cb: Button) = courseDeleteButton = cb
  def getCourseDeleteButton(): Button = courseDeleteButton

  private var courseUpdateButton: Button = null
  def setCourseUpdateButton(cb: Button) = courseUpdateButton = cb
  def getCourseUpdateButton(): Button = courseUpdateButton

  private var courseReadButton: Button = null
  def setCourseReadButton(cb: Button) = courseReadButton = cb
  def getCourseReadButton(): Button = courseReadButton

  def getCourseViewSelectedItem(): MutableCourse = courseView.getSelectionModel().getSelectedItem()

  /**
    * Reloads items to Course View and refreshes it
    */
  def reloadCourseViewItems() = {
    courseView.setItems(mkObservableList(Course.findAll().map(MutableCourse(_))))
    if (courseView.getItems().size > 0) {
      courseDeleteButton.setDisable(false)
      courseUpdateButton.setDisable(false)
      courseReadButton.setDisable(false)
    } else {
      courseDeleteButton.setDisable(true)
      courseUpdateButton.setDisable(true)
      courseReadButton.setDisable(true)
    }
    courseView.getSelectionModel().select(0)
  }
}

class CourseListController extends BaseController {

  /**
    * Specify type for table column
    *
    * @tparam T Mutable Class
    */
  type CourseTC[T] = TableColumn[MutableCourse, T]

  /**
    * Provide a table column and a generator function for the value to put into the column.
    *
    * @tparam T the type which is contained in the property
    * @return
    */
  def initTableViewColumn[T]: (CourseTC[T], (MutableCourse) => Any) => Unit = initTableViewColumnCellValueFactory[MutableCourse, T]

  @FXML var courseView: TableView[MutableCourse] = _
  @FXML var columnCourseId: CourseTC[Int] = _
  @FXML var columnCourseDescription: CourseTC[String] = _
  @FXML var columnCourseSemester: CourseTC[String] = _
  @FXML var columnCourseStart: CourseTC[String] = _
  @FXML var columnCourseEnd: CourseTC[String] = _
  @FXML var columnCoursePoints: CourseTC[Double] = _
  @FXML var courseQueryOutput: Label = _
  @FXML var courseCreateButton: Button = _
  @FXML var courseUpdateButton: Button = _
  @FXML var courseDeleteButton: Button = _
  @FXML var courseReadButton: Button = _
//  @FXML var courseQuery1Button: Button = _
//  @FXML var courseQuery2Button: Button = _

  val loaderCreatePath = "/fhj.swengb.project/course/create.fxml"
  val loaderUpdatePath = "/fhj.swengb.project/course/update.fxml"
  val loaderReadPath = "/fhj.swengb.project/course/read.fxml"

  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    CourseListController.setCourseView(courseView)
    CourseListController.setCourseUpdateButton(courseUpdateButton)
    CourseListController.setCourseDeleteButton(courseDeleteButton)
    CourseListController.setCourseReadButton(courseReadButton)
    CourseListController.reloadCourseViewItems()

    initTableViewColumn[Int](columnCourseId, _.idProperty)
    initTableViewColumn[String](columnCourseDescription, _.descriptionProperty)
    initTableViewColumn[String](columnCourseSemester, _.semesterProperty)
    initTableViewColumn[String](columnCourseStart, _.startProperty)
    initTableViewColumn[String](columnCourseEnd, _.endProperty)
    initTableViewColumn[Double](columnCoursePoints, _.pointsProperty)
  }

  /**
    *  Buttons on-click functions
    */

  def courseCreateButtonTrigger(): Unit = openWindow(loaderCreatePath, cssFilePath, CourseCreateController)

  def courseUpdateButtonTrigger(): Unit = {
    val record = CourseListController.getCourseViewSelectedItem()
    CourseUpdateController.setRecord(record)
    openWindow(loaderUpdatePath, cssFilePath, CourseUpdateController)
  }

  def courseDeleteButtonTrigger(): Unit = {
    val id: Int = CourseListController.getCourseViewSelectedItem().idProperty.getValue().toInt
    var course: Course = Course.findOne(id)
    course.delete()
    CourseListController.reloadCourseViewItems()
  }

  def courseReadButtonTrigger(): Unit = {
    val record = CourseListController.getCourseViewSelectedItem()
    CourseReadController.setRecord(record)
    openWindow(loaderReadPath, cssFilePath, CourseReadController)
  }

//  def courseQuery1ButtonTrigger(): Unit = sys.exit()
//
//  def courseQuery2ButtonTrigger(): Unit = sys.exit()

  def courseExportButtonTrigger(): Unit = {
    val fileChooser: FileChooser = new FileChooser
    val path = fileChooser.showSaveDialog(CourseListController.getStage())
    if (path == null)
      return

    val file = new PrintWriter(new File(path.toString))

    file.write("Id;Description;Semester;Start;End;Points\r\n");

    val courses: List[Course] = Course.findAll()
    for(course <- courses) {
      file.write(
        course.id + ";" +
        Helpers.prepareCsvString(course.description)+ ";" +
        course.semester + ";" +
        course.start + ";" +
        course.end + ";" +
        course.points +
        "\r\n")
    }

    file.close()
  }


}