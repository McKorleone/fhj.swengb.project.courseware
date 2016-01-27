package fhj.swengb.project.courseware.controllers.project

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml._
import javafx.scene.control.{Button, Label, TableColumn, TableView}
import fhj.swengb.project.courseware.components.JfxUtils._
import fhj.swengb.project.courseware.components.{Helpers, BaseController}
import fhj.swengb.project.courseware.models.Project
import fhj.swengb.project.courseware.models.MutableProject
import javafx.stage.FileChooser
import java.io._

object ProjectListController extends BaseController {
  private var projectView: TableView[MutableProject] = null
  def setProjectView(cv: TableView[MutableProject]) = projectView = cv
  def getProjectView(): TableView[MutableProject] = projectView

  private var projectDeleteButton: Button = null
  def setProjectDeleteButton(cb: Button) = projectDeleteButton = cb
  def getProjectDeleteButton(): Button = projectDeleteButton

  private var projectUpdateButton: Button = null
  def setProjectUpdateButton(cb: Button) = projectUpdateButton = cb
  def getProjectUpdateButton(): Button = projectUpdateButton

  private var projectReadButton: Button = null
  def setProjectReadButton(cb: Button) = projectReadButton = cb
  def getProjectReadButton(): Button = projectReadButton

  def getProjectViewSelectedItem(): MutableProject = projectView.getSelectionModel().getSelectedItem()

  /**
    * Reloads items to Course View and refreshes it
    */
  def reloadProjectViewItems() = {
    projectView.setItems(mkObservableList(Project.findAll().map(MutableProject(_))))
    if (projectView.getItems().size > 0) {
      projectDeleteButton.setDisable(false)
      projectUpdateButton.setDisable(false)
      projectReadButton.setDisable(false)
    } else {
      projectDeleteButton.setDisable(true)
      projectUpdateButton.setDisable(true)
      projectReadButton.setDisable(true)
    }
    projectView.getSelectionModel().select(0)
  }
}

class ProjectListController extends BaseController {

  /**
    * Specify type for table column
    *
    * @tparam T Mutable Class
    */
  type ProjectTC[T] = TableColumn[MutableProject, T]

  /**
    * Provide a table column and a generator function for the value to put into the column.
    *
    * @tparam T the type which is contained in the property
    * @return
    */
  def initTableViewColumn[T]: (ProjectTC[T], (MutableProject) => Any) => Unit = initTableViewColumnCellValueFactory[MutableProject, T]

  @FXML var projectView: TableView[MutableProject] = _
  @FXML var columnProjectNr: ProjectTC[Int] = _
  @FXML var columnProjectName: ProjectTC[String] = _
  @FXML var columnProjectDescription: ProjectTC[String] = _
  @FXML var columnProjectStartdate: ProjectTC[String] = _
  @FXML var columnProjectDeadline: ProjectTC[String] = _
  @FXML var columnProjectPoints: ProjectTC[Double] = _
  @FXML var projectQueryOutput: Label = _
  @FXML var projectCreateButton: Button = _
  @FXML var projectUpdateButton: Button = _
  @FXML var projectDeleteButton: Button = _
  @FXML var projectReadButton: Button = _
  //  @FXML var courseQuery1Button: Button = _
  //  @FXML var courseQuery2Button: Button = _

  val loaderCreatePath = "/fhj.swengb.project/project/create.fxml"
  val loaderUpdatePath = "/fhj.swengb.project/project/update.fxml"
  val loaderReadPath = "/fhj.swengb.project/project/read.fxml"

  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    ProjectListController.setProjectView(projectView)
    ProjectListController.setProjectUpdateButton(projectUpdateButton)
    ProjectListController.setProjectDeleteButton(projectDeleteButton)
    ProjectListController.setProjectReadButton(projectReadButton)
    ProjectListController.reloadProjectViewItems()

    initTableViewColumn[Int](columnProjectNr, _.nrProperty)
    initTableViewColumn[String](columnProjectName, _.nameProperty)
    initTableViewColumn[String](columnProjectDescription, _.descriptionProperty)
    initTableViewColumn[String](columnProjectStartdate, _.startdateProperty)
    initTableViewColumn[String](columnProjectDeadline, _.deadlineProperty)
    initTableViewColumn[Double](columnProjectPoints, _.pointsProperty)
  }

  /**
    *  Buttons on-click functions
    */

  def projectCreateButtonTrigger(): Unit = openWindow(loaderCreatePath, cssFilePath, ProjectCreateController)

  def projectUpdateButtonTrigger(): Unit = {
    val record = ProjectListController.getProjectViewSelectedItem()
    ProjectUpdateController.setRecord(record)
    openWindow(loaderUpdatePath, cssFilePath, ProjectUpdateController)
  }

  def projectDeleteButtonTrigger(): Unit = {
    val nr: Int = ProjectListController.getProjectViewSelectedItem().nrProperty.getValue().toInt
    var project: Project = Project.findOne(nr)
    project.delete()
    ProjectListController.reloadProjectViewItems()
  }

  def projectReadButtonTrigger(): Unit = {
    val record = ProjectListController.getProjectViewSelectedItem()
    ProjectReadController.setRecord(record)
    openWindow(loaderReadPath, cssFilePath, ProjectReadController)
  }

  //  def courseQuery1ButtonTrigger(): Unit = sys.exit()
  //
  //  def courseQuery2ButtonTrigger(): Unit = sys.exit()

  def projectExportButtonTrigger(): Unit = {
    val fileChooser: FileChooser = new FileChooser
    val path = fileChooser.showSaveDialog(ProjectListController.getStage())
    if (path == null)
      return

    val file = new PrintWriter(new File(path.toString))

    file.write("Nr;Name;Description;Startdate;Deadline;Points\r\n");

    val projects: List[Project] = Project.findAll()
    for(project <- projects) {
      file.write(
        project.nr + ";" +
          Helpers.prepareCsvString(project.name)+ ";" +
          project.description + ";" +
          project.startdate + ";" +
          project.deadline + ";" +
          project.points +
          "\r\n")
    }

    file.close()
  }


}