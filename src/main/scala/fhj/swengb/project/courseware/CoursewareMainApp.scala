package fhj.swengb.project.courseware

import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Label, Button}
import javafx.scene.{Scene, Parent}
import javafx.stage.Stage
import scala.util.control.NonFatal
import fhj.swengb.project.courseware.controllers.information.InformationController
import fhj.swengb.project.courseware.controllers.course.CourseListController
import fhj.swengb.project.courseware.controllers.exam.ExamListController
import fhj.swengb.project.courseware.controllers.homework.HomeworkListController
import fhj.swengb.project.courseware.controllers.lecture.LectureListController
import fhj.swengb.project.courseware.controllers.project.ProjectListController
import fhj.swengb.project.courseware.controllers.student.StudentListController
import fhj.swengb.project.courseware.components.BaseController
import fhj.swengb.project.courseware.models.{Course, Exam, Homework, Lecture, Project, Student}

/**
  * Initialize the app
  */
object CoursewareMainApp {

  /**
    * Creates Tables by starting of app if needed
    *
    * @return
    */
  def initTables() = {
    Course.createTable()
    Exam.createTable()
    Homework.createTable()
    Lecture.createTable()
    Project.createTable()
    Student.createTable()
  }

  /**
    * Point of entry of the app
    *
    * @param args
    */
  def main(args: Array[String]) {
    initTables()
    Application.launch(classOf[CoursewareMainApp], args: _*)
  }

}

/**
  * Opens main app window
  */
class CoursewareMainApp extends javafx.application.Application {
  val cssFilePath = "fhj.swengb.project/style.css"
  val loaderMainPath = "/fhj.swengb.project/main.fxml"

  override def start(stage: Stage): Unit = {
    try {
      val fxmlLoader = new FXMLLoader(getClass.getResource(loaderMainPath))
      fxmlLoader.load[Parent]()

      val scene = new Scene(fxmlLoader.getRoot[Parent])

      stage.setTitle("Courseware")
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssFilePath)
      stage.setResizable(false)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }
}

/**
  * Main Controller
  */
class CoursewareMainAppController extends BaseController {

  @FXML var courseware: Label = _
  @FXML var exitButton: Button = _
  @FXML var informationButton: Button = _
  @FXML var courseButton: Button = _
  @FXML var lectureButton: Button = _
  @FXML var homeworkButton: Button = _
  @FXML var projectButton: Button = _
  @FXML var examButton: Button = _
  @FXML var studentButton: Button = _

  val loaderCoursePath = "/fhj.swengb.project/course/list.fxml"
  val loaderStudentPath = "/fhj.swengb.project/student/list.fxml"
  val loaderLecturePath = "/fhj.swengb.project/lecture/list.fxml"
  val loaderHomeworkPath = "/fhj.swengb.project/homework/list.fxml"
  val loaderProjectPath = "/fhj.swengb.project/project/list.fxml"
  val loaderExamPath = "/fhj.swengb.project/exam/list.fxml"
  val loaderInformationPath = "/fhj.swengb.project/information/page.fxml"

  /**
    * Init function of the controller
    *
    * @param location
    * @param resources
    */
  override def initialize(location: URL, resources: ResourceBundle): Unit = {}

  /**
    * Buttons on-click functions
    */

  def exitButtonTrigger(): Unit = sys.exit()

  def informationButtonTrigger(): Unit = openWindow(loaderInformationPath, cssFilePath, InformationController)

  def courseButtonTrigger(): Unit = openWindow(loaderCoursePath, cssFilePath, CourseListController)

  def lectureButtonTrigger(): Unit = openWindow(loaderLecturePath, cssFilePath, LectureListController)

  def homeworkButtonTrigger(): Unit = openWindow(loaderHomeworkPath, cssFilePath, HomeworkListController)

  def projectButtonTrigger(): Unit = openWindow(loaderProjectPath, cssFilePath, ProjectListController)

  def examButtonTrigger(): Unit = openWindow(loaderExamPath, cssFilePath, ExamListController)

  def studentButtonTrigger(): Unit = openWindow(loaderStudentPath, cssFilePath, StudentListController)

}
