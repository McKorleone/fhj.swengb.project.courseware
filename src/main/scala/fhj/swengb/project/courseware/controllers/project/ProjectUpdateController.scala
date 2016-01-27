package fhj.swengb.project.courseware.controllers.project

import java.net.URL
import java.time.LocalDate
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button, TextField, DatePicker}
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.{MutableProject, Project}

object ProjectUpdateController extends BaseController {
  private var record: MutableProject = null
  def getRecord(): MutableProject = record
  def setRecord(r: MutableProject) = record = r
}

class ProjectUpdateController extends BaseController {

  @FXML var updateProjectButton: Button = _
  @FXML var cancelButton: Button = _
  @FXML var nameProject: TextField = _
  @FXML var descriptionProject: TextField = _
  @FXML var startdateProject: DatePicker = _
  @FXML var deadlineProject: DatePicker = _
  @FXML var pointsProject: TextField = _
  @FXML var invalidInput: Label = _
  @FXML var nameProjectError: Label = _
  @FXML var descriptionProjectError: Label = _
  @FXML var startdateProjectError: Label = _
  @FXML var deadlineProjectError: Label = _
  @FXML var pointsProjectError: Label = _
  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = ProjectUpdateController.getRecord()
    var project = Project.findOne(record.nrProperty.getValue())

   nameProject.setText(project.name)
    descriptionProject.setText(project.description)
    startdateProject.setValue(LocalDate.parse(project.startdate))
    deadlineProject.setValue(LocalDate.parse(project.deadline))
    pointsProject.setText(project.points.toString)
  }

  /**
    *  Buttons on-click functions
    */

  def updateProjectButtonTrigger(): Unit = {
    val nr: Int = ProjectUpdateController.getRecord().nrProperty.getValue.toInt
    var project: Project = Project.findOne(nr)

    nameProjectError.getStyleClass().add("hidden")
    descriptionProjectError.getStyleClass().add("hidden")
    startdateProjectError.getStyleClass().add("hidden")
    deadlineProjectError.getStyleClass().add("hidden")
    pointsProjectError.getStyleClass().add("hidden")

    project.attributes.put("name", nameProject.getText())
    project.attributes.put("description", descriptionProject.getText())
    project.attributes.put("startdate", if (startdateProject.getValue() == null) "" else startdateProject.getValue().toString)
    project.attributes.put("deadline", if (deadlineProject.getValue() == null) "" else deadlineProject.getValue().toString)
    project.attributes.put("points", pointsProject.getText())

    if (!project.update()) {
      if (project.hasError("name")) {
        nameProjectError.getStyleClass().clear()
        nameProjectError.getStyleClass().remove("hidden")
      }
      if (project.hasError("description")) {
        descriptionProjectError.getStyleClass().clear()
        descriptionProjectError.getStyleClass().remove("hidden")
      }
      if (project.hasError("startdate")) {
        startdateProjectError.getStyleClass().clear()
        startdateProjectError.getStyleClass().remove("hidden")
      }
      if (project.hasError("deadline")) {
        deadlineProjectError.getStyleClass().clear()
        deadlineProjectError.getStyleClass().remove("hidden")
      }
      if (project.hasError("points")) {
        pointsProjectError.getStyleClass().clear()
        pointsProjectError.getStyleClass().remove("hidden")
      }
    } else {
      ProjectUpdateController.getStage().hide()
      ProjectListController.reloadProjectViewItems()
      ProjectListController.getProjectView().getSelectionModel().select(0)
    }
  }

  def cancelButtonTrigger(): Unit = ProjectUpdateController.getStage().hide
}
