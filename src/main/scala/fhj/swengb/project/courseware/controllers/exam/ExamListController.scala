package fhj.swengb.project.courseware.controllers.exam

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml._
import javafx.scene.control.{Button, Label, TableColumn, TableView}
import fhj.swengb.project.courseware.components.JfxUtils._
import fhj.swengb.project.courseware.components.{Helpers, BaseController}
import fhj.swengb.project.courseware.models.Exam
import fhj.swengb.project.courseware.models.MutableExam
import javafx.stage.FileChooser
import java.io._

object ExamListController extends BaseController {
  private var examView: TableView[MutableExam] = null
  def setExamView(cv: TableView[MutableExam]) = examView = cv
  def getExamView(): TableView[MutableExam] = examView

  private var examDeleteButton: Button = null
  def setExamDeleteButton(cb: Button) = examDeleteButton = cb
  def getExamDeleteButton(): Button = examDeleteButton

  private var examUpdateButton: Button = null
  def setExamUpdateButton(cb: Button) = examUpdateButton = cb
  def getExamUpdateButton(): Button = examUpdateButton

  private var examReadButton: Button = null
  def setExamReadButton(cb: Button) = examReadButton = cb
  def getExamReadButton(): Button = examReadButton

  def getExamViewSelectedItem(): MutableExam = examView.getSelectionModel().getSelectedItem()

  /**
    * Reloads items to Exam View and refreshes it
    */
  def reloadExamViewItems() = {
    examView.setItems(mkObservableList(Exam.findAll().map(MutableExam(_))))
    if (examView.getItems().size > 0) {
      examDeleteButton.setDisable(false)
      examUpdateButton.setDisable(false)
      examReadButton.setDisable(false)
    } else {
      examDeleteButton.setDisable(true)
      examUpdateButton.setDisable(true)
      examReadButton.setDisable(true)
    }
    examView.getSelectionModel().select(0)
  }
}

class ExamListController extends BaseController {

  /**
    * Specify type for table column
    *
    * @tparam T Mutable Class
    */
  type ExamTC[T] = TableColumn[MutableExam, T]

  /**
    * Provide a table column and a generator function for the value to put into the column.
    *
    * @tparam T the type which is contained in the property
    * @return
    */
  def initTableViewColumn[T]: (ExamTC[T], (MutableExam) => Any) => Unit = initTableViewColumnCellValueFactory[MutableExam, T]

  @FXML var examView: TableView[MutableExam] = _
  @FXML var columnExamId: ExamTC[Int] = _
  @FXML var columnExamDescription: ExamTC[String] = _
  @FXML var columnExamStart: ExamTC[String] = _
  @FXML var columnExamEnd: ExamTC[String] = _
  @FXML var columnExamDate: ExamTC[String] = _
  @FXML var columnExamPoints: ExamTC[Double] = _
  @FXML var examQueryOutput: Label = _
  @FXML var examCreateButton: Button = _
  @FXML var examUpdateButton: Button = _
  @FXML var examDeleteButton: Button = _
  @FXML var examReadButton: Button = _
//  @FXML var examQuery1Button: Button = _
//  @FXML var examQuery2Button: Button = _

  val loaderCreatePath = "/fhj.swengb.project/exam/create.fxml"
  val loaderUpdatePath = "/fhj.swengb.project/exam/update.fxml"
  val loaderReadPath = "/fhj.swengb.project/exam/read.fxml"

  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    ExamListController.setExamView(examView)
    ExamListController.setExamUpdateButton(examUpdateButton)
    ExamListController.setExamDeleteButton(examDeleteButton)
    ExamListController.setExamReadButton(examReadButton)
    ExamListController.reloadExamViewItems()

    initTableViewColumn[Int](columnExamId, _.idProperty)
    initTableViewColumn[String](columnExamDescription, _.descriptionProperty)
    initTableViewColumn[String](columnExamStart, _.startProperty)
    initTableViewColumn[String](columnExamEnd, _.endProperty)
    initTableViewColumn[String](columnExamDate, _.dateProperty)
    initTableViewColumn[Double](columnExamPoints, _.pointsProperty)
  }

  /**
    *  Buttons on-click functions
    */

  def examCreateButtonTrigger(): Unit = openWindow(loaderCreatePath, cssFilePath, ExamCreateController)

  def examUpdateButtonTrigger(): Unit = {
    val record = ExamListController.getExamViewSelectedItem()
    ExamUpdateController.setRecord(record)
    openWindow(loaderUpdatePath, cssFilePath, ExamUpdateController)
  }

  def examDeleteButtonTrigger(): Unit = {
    val id: Int = ExamListController.getExamViewSelectedItem().idProperty.getValue().toInt
    var exam: Exam = Exam.findOne(id)
    exam.delete()
    ExamListController.reloadExamViewItems()
  }

  def examReadButtonTrigger(): Unit = {
    val record = ExamListController.getExamViewSelectedItem()
    ExamReadController.setRecord(record)
    openWindow(loaderReadPath, cssFilePath, ExamReadController)
  }

//  def examQuery1ButtonTrigger(): Unit = sys.exit()
//
//  def examQuery2ButtonTrigger(): Unit = sys.exit()

  def examExportButtonTrigger(): Unit = {
    val fileChooser: FileChooser = new FileChooser
    val path = fileChooser.showSaveDialog(ExamListController.getStage())
    if (path == null)
      return

    val file = new PrintWriter(new File(path.toString))

    file.write("Id;Description;Start Time;End Time;Date;Max Points\r\n");

    val exams: List[Exam] = Exam.findAll()
    for(exam <- exams) {
      file.write(
        exam.id + ";" +
        Helpers.prepareCsvString(exam.description)+ ";" +
        exam.start + ";" +
        exam.end + ";" +
        exam.date + ";" +
        exam.points +
        "\r\n")
    }

    file.close()
  }


}