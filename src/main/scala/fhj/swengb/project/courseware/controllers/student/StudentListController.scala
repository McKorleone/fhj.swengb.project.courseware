package fhj.swengb.project.courseware.controllers.student

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml._
import javafx.scene.control.{Button, Label, TableColumn, TableView}
import fhj.swengb.project.courseware.components.JfxUtils._
import fhj.swengb.project.courseware.components.{Helpers, BaseController}
import fhj.swengb.project.courseware.models.Student
import fhj.swengb.project.courseware.models.MutableStudent
import javafx.stage.FileChooser
import java.io._

object StudentListController extends BaseController {
  private var studentView: TableView[MutableStudent] = null
  def setStudentView(cv: TableView[MutableStudent]) = studentView = cv
  def getStudentView(): TableView[MutableStudent] = studentView

  private var studentDeleteButton: Button = null
  def setStudentDeleteButton(cb: Button) = studentDeleteButton = cb
  def getStudentDeleteButton(): Button = studentDeleteButton

  private var studentUpdateButton: Button = null
  def setStudentUpdateButton(cb: Button) = studentUpdateButton = cb
  def getStudentUpdateButton(): Button = studentUpdateButton

  private var studentReadButton: Button = null
  def setStudentReadButton(cb: Button) = studentReadButton = cb
  def getStudentReadButton(): Button = studentReadButton

  def getStudentViewSelectedItem(): MutableStudent = studentView.getSelectionModel().getSelectedItem()

  /**
    * Reloads items to Student View and refreshes it
    */
  def reloadStudentViewItems() = {
    studentView.setItems(mkObservableList(Student.findAll().map(MutableStudent(_))))
    if (studentView.getItems().size > 0) {
      studentDeleteButton.setDisable(false)
      studentUpdateButton.setDisable(false)
      studentReadButton.setDisable(false)
    } else {
      studentDeleteButton.setDisable(true)
      studentUpdateButton.setDisable(true)
      studentReadButton.setDisable(true)
    }
    studentView.getSelectionModel().select(0)
  }
}

class StudentListController extends BaseController {

  /**
    * Specify type for table column
    *
    * @tparam T Mutable Class
    */
  type StudentTC[T] = TableColumn[MutableStudent, T]

  /**
    * Provide a table column and a generator function for the value to put into the column.
    *
    * @tparam T the type which is contained in the property
    * @return
    */
  def initTableViewColumn[T]: (StudentTC[T], (MutableStudent) => Any) => Unit = initTableViewColumnCellValueFactory[MutableStudent, T]

  @FXML var studentView: TableView[MutableStudent] = _
  @FXML var columnStudentId: StudentTC[Int] = _
  @FXML var columnStudentFirstName: StudentTC[String] = _
  @FXML var columnStudentLastName: StudentTC[String] = _
  @FXML var columnStudentGroup: StudentTC[Int] = _
  @FXML var columnStudentPoints: StudentTC[Double] = _
  @FXML var columnStudentPercentage: StudentTC[Int] = _
  @FXML var columnStudentGrade: StudentTC[Int] = _
  @FXML var studentQueryOutput: Label = _
  @FXML var studentCreateButton: Button = _
  @FXML var studentUpdateButton: Button = _
  @FXML var studentDeleteButton: Button = _
  @FXML var studentReadButton: Button = _
//  @FXML var studentQuery1Button: Button = _
//  @FXML var studentQuery2Button: Button = _

  val loaderCreatePath = "/fhj.swengb.project/student/create.fxml"
  val loaderUpdatePath = "/fhj.swengb.project/student/update.fxml"
  val loaderReadPath = "/fhj.swengb.project/student/read.fxml"

  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    StudentListController.setStudentView(studentView)
    StudentListController.setStudentUpdateButton(studentUpdateButton)
    StudentListController.setStudentDeleteButton(studentDeleteButton)
    StudentListController.setStudentReadButton(studentReadButton)
    StudentListController.reloadStudentViewItems()

    initTableViewColumn[Int](columnStudentId, _.idProperty)
    initTableViewColumn[String](columnStudentFirstName, _.firstNameProperty)
    initTableViewColumn[String](columnStudentLastName, _.lastNameProperty)
    initTableViewColumn[Int](columnStudentGroup, _.groupProperty)
    initTableViewColumn[Double](columnStudentPoints, _.pointsProperty)
    initTableViewColumn[Int](columnStudentPercentage, _.percentageProperty)
    initTableViewColumn[Int](columnStudentGrade, _.gradeProperty)
  }

  /**
    *  Buttons on-click functions
    */

  def studentCreateButtonTrigger(): Unit = openWindow(loaderCreatePath, cssFilePath, StudentCreateController)

  def studentUpdateButtonTrigger(): Unit = {
    val record = StudentListController.getStudentViewSelectedItem()
    StudentUpdateController.setRecord(record)
    openWindow(loaderUpdatePath, cssFilePath, StudentUpdateController)
  }

  def studentDeleteButtonTrigger(): Unit = {
    val id: Int = StudentListController.getStudentViewSelectedItem().idProperty.getValue().toInt
    var student: Student = Student.findOne(id)
    student.delete()
    StudentListController.reloadStudentViewItems()
  }

  def studentReadButtonTrigger(): Unit = {
    val record = StudentListController.getStudentViewSelectedItem()
    StudentReadController.setRecord(record)
    openWindow(loaderReadPath, cssFilePath, StudentReadController)
  }

//  def studentQuery1ButtonTrigger(): Unit = sys.exit()
//
//  def studentQuery2ButtonTrigger(): Unit = sys.exit()

  def studentExportButtonTrigger(): Unit = {
    val fileChooser: FileChooser = new FileChooser
    val path = fileChooser.showSaveDialog(StudentListController.getStage())
    if (path == null)
      return

    val file = new PrintWriter(new File(path.toString))

    file.write("Id;First Name;Last Name;Group;Points;Percentage;Grade\r\n");

    val students: List[Student] = Student.findAll()
    for(student <- students) {
      file.write(
        student.id + ";" +
        Helpers.prepareCsvString(student.first_name)+ ";" +
        Helpers.prepareCsvString(student.last_name)+ ";" +
        student.group + ";" +
        student.points + ";" +
        student.percentage + ";" +
        student.grade +
        "\r\n")
    }

    file.close()
  }


}