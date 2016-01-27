package fhj.swengb.project.courseware.components

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXMLLoader, Initializable}
import javafx.scene.{Scene, Parent}
import javafx.stage.Stage

class BaseController extends Initializable {
  var stage: Stage = null
  val cssFilePath = "fhj.swengb.project/style.css"

  def setStage(s: Stage) = stage = s
  def getStage(): Stage = stage

  override def initialize(location: URL, resources: ResourceBundle) = {}


  def openWindow(fxmlLoaderPath: String, cssPath: String, controller: BaseController) = {
    val fxmlLoader = new FXMLLoader(getClass.getResource(fxmlLoaderPath))
    fxmlLoader.load[Parent]()

    val scene = new Scene(fxmlLoader.getRoot[Parent])
    val stage = new Stage

    stage.setTitle("Courseware")
    stage.setScene(scene)
    stage.getScene.getStylesheets.add(cssPath)
    stage.setResizable(false)
    stage.show()

    controller.setStage(stage)
  }

}