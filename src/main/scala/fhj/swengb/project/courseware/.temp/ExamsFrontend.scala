/*
package fhj.swengb.project.courseware


  import java.net.URL
  import java.util.ResourceBundle

  import javafx.fxml._
  import javafx.scene.control.{Button, Label, TableColumn, TableView}

import fhj.swengb.project.courseware.CourseCode.CourseController
import CourseController.JfxUtils
import fhj.swengb.project.courseware.CoursewareEntities.MutableExam



  object DataSourceExam {

    val examsList = CoursewareMainApp.listOfExams
    val data =
      (0 to (examsList.length - 1)) map {
        case i => examsList(i)
        // hier soll aber die Information aus der Tabelle von der Datenbank rausgeholt werden
      }
  }


  class ExamController extends Initializable {

    import JfxUtils._

    type ExamTC[T] = TableColumn[MutableExam, T]

    @FXML var examView: TableView[MutableExam] = _
    @FXML var columnExamId: ExamTC[Int] = _
    @FXML var columnExamDescription: ExamTC[String] = _
    @FXML var columnExamStart: ExamTC[String] = _
    @FXML var columnExamEnd: ExamTC[String] = _
    @FXML var columnExamDate: ExamTC[String] = _
    @FXML var columnExamPoints: ExamTC[Double] = _
    @FXML var examQueryOutput: Label = _
    @FXML var examCreate: Button = _
    @FXML var examUpdate: Button = _
    @FXML var examDelete: Button = _
    @FXML var examRead: Button = _
    @FXML var examQuery1: Button = _
    @FXML var examQuery2: Button = _
    @FXML var examBack: Button = _

    val mutableExams = mkObservableList(DataSourceExam.data.map(MutableExam(_)))

    /**
      * provide a table column and a generator function for the value to put into
      * the column.
      *
      * @tparam T the type which is contained in the property
      * @return
      */
    def initTableViewColumn[T]: (ExamTC[T], (MutableExam) => Any) => Unit =
      initTableViewColumnCellValueFactory[MutableExam, T]

    override def initialize(location: URL, resources: ResourceBundle): Unit = {

      examView.setItems(mutableExams)

      initTableViewColumn[Int](columnExamId, _.idProperty)
      initTableViewColumn[String](columnExamDescription, _.descriptionProperty)
      initTableViewColumn[String](columnExamStart, _.startProperty)
      initTableViewColumn[String](columnExamEnd, _.endProperty)
      initTableViewColumn[String](columnExamDate, _.dateProperty)
      initTableViewColumn[Double](columnExamPoints, _.pointsProperty)

    }

    def examCreateButton(): Unit = sys.exit()
    def examUpdateButton(): Unit = sys.exit()
    def examDeleteButton(): Unit = sys.exit()
    def examReadButton(): Unit = sys.exit()
    def examQuery1Button(): Unit = sys.exit()
    def examQuery2Button(): Unit = sys.exit()
    def examExportButton(): Unit = sys.exit()
    def examBackButton(): Unit = sys.exit()

}
*/