package fhj.swengb.project.courseware.controllers.project

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.Label
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.MutableProject

object ProjectReadController extends BaseController {
  private var record: MutableProject = null
  def getRecord(): MutableProject = record
  def setRecord(r: MutableProject) = record = r
}

class ProjectReadController extends BaseController {

  @FXML var nrProject: Label = _
  @FXML var nameProject: Label = _
  @FXML var descriptionProject: Label = _
  @FXML var startdateProject: Label = _
  @FXML var deadlineProject: Label = _
  @FXML var pointsProject: Label = _

  /**
    * @inheritdoc
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = ProjectReadController.getRecord()

    nrProject.setText(record.nrProperty.getValue().toString)
    nameProject.setText(record.nameProperty.getValue())
    descriptionProject.setText(record.descriptionProperty.getValue())
    startdateProject.setText(record.startdateProperty.getValue())
    deadlineProject.setText(record.deadlineProperty.getValue())
    pointsProject.setText(record.pointsProperty.getValue().toString)
  }

  /**
    *  Buttons on-click functions
    */
}