package fhj.swengb.project.courseware.models

import java.sql.ResultSet
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty, SimpleIntegerProperty}
import scala.collection.mutable.ListBuffer
import fhj.swengb.project.courseware.components.DbTrait
import fhj.swengb.project.courseware.components.DbTrait.DbEntity
import fhj.swengb.project.courseware.components.Validator

object Course extends DbEntity[Course] {

  val dropTableSql = "DROP TABLE IF EXISTS Course"
  val createTableSql = "CREATE TABLE IF NOT EXISTS Course (id INTEGER PRIMARY KEY AUTOINCREMENT, description STRING, semester INTEGER, start DATE, \"end\" DATE, points DOUBLE);"
  val selectAllSql = "SELECT * FROM Course"
  val selectSql = "SELECT * FROM Course WHERE id=?"

  /**
    * @inheritdoc
    */
  def validate: Boolean = false

  /**
    * @inheritdoc
    */
  def createTable(): Boolean = {
    DbTrait.connection.prepareStatement(createTableSql).executeUpdate() == 1
  }

  /**
    * @inheritdoc
    */
  def create(): Boolean = false

  /**
    * @inheritdoc
    */
  def update(): Boolean = false

  /**
    * @inheritdoc
    */
  def delete(): Boolean = false

  /**
    * @inheritdoc
    */
  def fetch(rs: ResultSet): List[Course] = {
    val lb: ListBuffer[Course] = new ListBuffer[Course]()
    while (rs.next()) lb.append(Course(rs.getInt("id"), rs.getString("description"), rs.getInt("semester"), rs.getString("start"), rs.getString("end"), rs.getDouble("points")))
    lb.toList
  }

  /**
    * Finds all records in Course table
    *
    * @return List of Course
    */
  def findAll(): List[Course] = {
    fetch(query(selectAllSql))
  }

  /**
    * Finds record of Course table by id
    *
    * @param id Id of Course
    * @return Course
    */
  def findOne(id: Int): Course = {
    val pstmt = DbTrait.connection.prepareStatement(selectSql)
    pstmt.setInt(1, id)
    fetch(pstmt.executeQuery())(0)
  }

}

case class Course(id: Int = 0, var description: String = "", var semester: Int = 0, var start: String = "", var end: String = "", var points: Double = 0.0) extends DbEntity[Course] {

  val insertSql = "INSERT INTO Course (description, semester, start, end, points) VALUES (?, ?, ?, ?, ?)"
  val updateSql = "UPDATE Course SET description=?, semester=?, start=?, end=?, points=? WHERE id=?"
  val deleteSql = "DELETE FROM Course WHERE id=?"

  /**
    * @inheritdoc
    */
  def validate(): Boolean = {

    // validating
    for(attribute <- attributes) {
      val key = attribute._1
      val value = attribute._2

      key match {
        case "description" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
        }
        case "semester" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isInteger(value)) errors.put(key, ERROR_INTEGER)
          else if (value.toInt < 1 || value.toInt > 6) errors.put(key, "The Value must be in range from 1 to 6")
        }
        case "start" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDatestamp(value)) errors.put(key, ERROR_DATESTAMP)
        }
        case "end" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDatestamp(value)) errors.put(key, ERROR_DATESTAMP)
        }
        case "points" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDouble(value)) errors.put(key, ERROR_DOUBLE)
        }
        case _ => {}
      }
    }

    // putting values to class properties if no errors
    if (errors.size == 0) {
      for(attribute <- attributes) {
        val key = attribute._1
        val value = attribute._2

        key match {
          case "description" => description = value
          case "semester" => semester = value.toInt
          case "start" => start = value
          case "end" => end = value
          case "points" => points = value.toDouble
          case _ => {}
        }
      }
    }

    !hasErrors()
  }

  /**
    * @inheritdoc
    */
  def createTable(): Boolean = false

  /**
    * @inheritdoc
    */
  def create(): Boolean = {
    if (!validate())
      return false

    val pstmt = DbTrait.connection.prepareStatement(insertSql)
    pstmt.setString(1, description)
    pstmt.setInt(2, semester)
    pstmt.setString(3, start)
    pstmt.setString(4, end)
    pstmt.setDouble(5, points)

    pstmt.executeUpdate() == 1
  }

  /**
    * @inheritdoc
    */
  def update(): Boolean = {
    if (!validate())
      return false

    val pstmt = DbTrait.connection.prepareStatement(updateSql)
    pstmt.setString(1, description)
    pstmt.setInt(2, semester)
    pstmt.setString(3, start)
    pstmt.setString(4, end)
    pstmt.setDouble(5, points)
    pstmt.setInt(6, id)

    pstmt.executeUpdate() == 1
  }

  /**
    * @inheritdoc
    */
  def delete(): Boolean = {
    val pstmt = DbTrait.connection.prepareStatement(deleteSql)
    pstmt.setInt(1, id)

    pstmt.executeUpdate() == 1
  }

  /**
    * @inheritdoc
    */
  def fetch(rs: ResultSet): List[Course] = List()

}

object MutableCourse {
  def apply(c: Course): MutableCourse = {
    val mc = new MutableCourse
    mc.setId(c.id)
    mc.setDescription(c.description)
    mc.setSemester(c.semester)
    mc.setStart(c.start)
    mc.setEnd(c.end)
    mc.setPoints(c.points)
    mc
  }
}

class MutableCourse {
  val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
  val semesterProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val startProperty: SimpleStringProperty = new SimpleStringProperty()
  val endProperty: SimpleStringProperty = new SimpleStringProperty()
  val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

  def setId(id: Int) = idProperty.set(id)
  def setDescription(description: String) = descriptionProperty.set(description)
  def setSemester(semester: Int) = semesterProperty.set(semester)
  def setStart(start: String) = startProperty.set(start)
  def setEnd(end: String) = endProperty.set(end)
  def setPoints(points: Double) = pointsProperty.set(points)
}