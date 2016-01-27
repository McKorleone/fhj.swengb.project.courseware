package fhj.swengb.project.courseware.controllers.homework

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml._
import javafx.scene.control.{Button, Label, TableColumn, TableView}
import fhj.swengb.project.courseware.components.JfxUtils._
import fhj.swengb.project.courseware.components.{Helpers, BaseController}
import fhj.swengb.project.courseware.models.Homework
import fhj.swengb.project.courseware.models.MutableHomework
import javafx.stage.FileChooser
import java.io._

object HomeworkListController extends BaseController {
  private var homeworkView: TableView[MutableHomework] = null
  def setHomeworkView(cv: TableView[MutableHomework]) = homeworkView = cv
  def getHomeworkView(): TableView[MutableHomework] = homeworkView

  private var homeworkDeleteButton: Button = null
  def setHomeworkDeleteButton(cb: Button) = homeworkDeleteButton = cb
  def getHomeworkDeleteButton(): Button = homeworkDeleteButton

  private var homeworkUpdateButton: Button = null
  def setHomeworkUpdateButton(cb: Button) = homeworkUpdateButton = cb
  def getHomeworkUpdateButton(): Button = homeworkUpdateButton

  private var homeworkReadButton: Button = null
  def setHomeworkReadButton(cb: Button) = homeworkReadButton = cb
  def getHomeworkReadButton(): Button = homeworkReadButton

  def getHomeworkViewSelectedItem(): MutableHomework = homeworkView.getSelectionModel().getSelectedItem()

  /**
    * Reloads items to Course View and refreshes it
    */
  def reloadHomeworkViewItems() = {
    homeworkView.setItems(mkObservableList(Homework.findAll().map(MutableHomework(_))))
    if (homeworkView.getItems().size > 0) {
      homeworkDeleteButton.setDisable(false)
     homeworkUpdateButton.setDisable(false)
     homeworkReadButton.setDisable(false)
    } else {
      homeworkDeleteButton.setDisable(true)
      homeworkUpdateButton.setDisable(true)
     homeworkReadButton.setDisable(true)
    }
   homeworkView.getSelectionModel().select(0)
  }
}

class HomeworkListController extends BaseController {

  /**
    * Specify type for table column
    *
    * @tparam T Mutable Class
    */
  type HomeworkTC[T] = TableColumn[MutableHomework, T]

  /**
    * Provide a table column and a generator function for the value to put into the column.
    *
    * @tparam T the type which is contained in the property
    * @return
    */
  def initTableViewColumn[T]: (HomeworkTC[T], (MutableHomework) => Any) => Unit = initTableViewColumnCellValueFactory[MutableHomework, T]

  @FXML var homeworkView: TableView[MutableHomework] = _
  @FXML var columnHomeworkNr: HomeworkTC[Int] = _
  @FXML var columnHomeworkDescription: HomeworkTC[String] = _
  @FXML var columnHomeworkDate: HomeworkTC[String] = _
  @FXML var columnHomeworkDeadline: HomeworkTC[String] = _
  @FXML var columnHomeworkPoints: HomeworkTC[Double] = _
  @FXML var homeworkQueryOutput: Label = _
  @FXML var homeworkCreateButton: Button = _
  @FXML var homeworkUpdateButton: Button = _
  @FXML var homeworkDeleteButton: Button = _
  @FXML var homeworkReadButton: Button = _
  //  @FXML var courseQuery1Button: Button = _
  //  @FXML var courseQuery2Button: Button = _

  val loaderCreatePath = "/fhj.swengb.project/homework/create.fxml"
  val loaderUpdatePath = "/fhj.swengb.project/homework/update.fxml"
  val loaderReadPath = "/fhj.swengb.project/homework/read.fxml"

  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    HomeworkListController.setHomeworkView(homeworkView)
    HomeworkListController.setHomeworkUpdateButton(homeworkUpdateButton)
    HomeworkListController.setHomeworkDeleteButton(homeworkDeleteButton)
    HomeworkListController.setHomeworkReadButton(homeworkReadButton)
    HomeworkListController.reloadHomeworkViewItems()

    initTableViewColumn[Int](columnHomeworkNr, _.nrProperty)
    initTableViewColumn[String](columnHomeworkDescription, _.descriptionProperty)
    initTableViewColumn[String](columnHomeworkDate, _.dateProperty)
    initTableViewColumn[String](columnHomeworkDeadline, _.deadlineProperty)
    initTableViewColumn[Double](columnHomeworkPoints, _.pointsProperty)
  }

  /**
    *  Buttons on-click functions
    */

  def homeworkCreateButtonTrigger(): Unit = openWindow(loaderCreatePath, cssFilePath, HomeworkCreateController)

  def homeworkUpdateButtonTrigger(): Unit = {
    val record = HomeworkListController.getHomeworkViewSelectedItem()
    HomeworkUpdateController.setRecord(record)
    openWindow(loaderUpdatePath, cssFilePath, HomeworkUpdateController)
  }

  def homeworkDeleteButtonTrigger(): Unit = {
    val nr: Int = HomeworkListController.getHomeworkViewSelectedItem().nrProperty.getValue().toInt
    var homework: Homework = Homework.findOne(nr)
   homework.delete()
    HomeworkListController.reloadHomeworkViewItems()
  }

  def homeworkReadButtonTrigger(): Unit = {
    val record = HomeworkListController.getHomeworkViewSelectedItem()
    HomeworkReadController.setRecord(record)
    openWindow(loaderReadPath, cssFilePath, HomeworkReadController)
  }

  //  def courseQuery1ButtonTrigger(): Unit = sys.exit()
  //
  //  def courseQuery2ButtonTrigger(): Unit = sys.exit()

  def homeworkExportButtonTrigger(): Unit = {
    val fileChooser: FileChooser = new FileChooser
    val path = fileChooser.showSaveDialog(HomeworkListController.getStage())
    if (path == null)
      return

    val file = new PrintWriter(new File(path.toString))

    file.write("NR;Description;Date;Deadline;Points\r\n");

    val homeworks: List[Homework] = Homework.findAll()
    for(homework <- homeworks) {
      file.write(
        homework.nr + ";" +
          Helpers.prepareCsvString(homework.description)+ ";" +
          homework.date + ";" +
          homework.deadline + ";" +
          homework.points +
          "\r\n")
    }

    file.close()
  }


}