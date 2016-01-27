package fhj.swengb.project.courseware.controllers.homework

/**
  * Created by Felix on 26.01.2016.
  */

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.Label
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.MutableHomework

object HomeworkReadController extends BaseController {
  private var record: MutableHomework = null
  def getRecord(): MutableHomework = record
  def setRecord(r: MutableHomework) = record = r
}

class HomeworkReadController extends BaseController {

  @FXML var nrHomework: Label = _
  @FXML var descriptionHomework: Label = _
  @FXML var dateHomework: Label = _
  @FXML var deadlineHomework: Label = _
  @FXML var pointsHomework: Label = _

  /**
    * @inheritdoc
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val record = HomeworkReadController.getRecord()

    nrHomework.setText(record.nrProperty.getValue().toString)
    descriptionHomework.setText(record.descriptionProperty.getValue())
    dateHomework.setText(record.dateProperty.getValue().toString)
    deadlineHomework.setText(record.deadlineProperty.getValue())
    pointsHomework.setText(record.pointsProperty.getValue().toString())
  }

  /**
    *  Buttons on-click functions
    */
}




