package fhj.swengb.project.courseware.models

import java.sql.ResultSet
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty, SimpleIntegerProperty}
import scala.collection.mutable.ListBuffer
import fhj.swengb.project.courseware.components.DbTrait
import fhj.swengb.project.courseware.components.DbTrait.DbEntity
import fhj.swengb.project.courseware.components.Validator

object Exam extends DbEntity[Exam] {

  val dropTableSql = "DROP TABLE IF EXISTS Exam"
  val createTableSql = "CREATE TABLE IF NOT EXISTS Exam (id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, start STRING, \"end\" STRING, date DATE, points DOUBLE)"
  val selectAllSql = "SELECT * FROM Exam"
  val selectSql = "SELECT * FROM Exam WHERE id=?"

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
  def fetch(rs: ResultSet): List[Exam] = {
    val lb: ListBuffer[Exam] = new ListBuffer[Exam]()
    while (rs.next()) lb.append(Exam(rs.getInt("id"), rs.getString("description"), rs.getString("start"), rs.getString("end"), rs.getString("date"), rs.getDouble("points")))
    lb.toList
  }

  /**
    * Finds all records in Exam table
    *
    * @return List of Exam
    */
  def findAll(): List[Exam] = {
    fetch(query(selectAllSql))
  }

  /**
    * Finds record of Exam table by id
    *
    * @param id Id of Exam
    * @return Exam
    */
  def findOne(id: Int): Exam = {
    val pstmt = DbTrait.connection.prepareStatement(selectSql)
    pstmt.setInt(1, id)
    fetch(pstmt.executeQuery())(0)
  }

}

case class Exam(id: Int = 0, var description: String = "", var start: String = "", var end: String = "", var date: String = "", var points: Double = 0.0) extends DbEntity[Exam] {

  val insertSql = "INSERT INTO Exam (description, start, end, date, points) VALUES (?, ?, ?, ?, ?)"
  val updateSql = "UPDATE Exam SET description=?, start=?, end=?, date=?, points=? WHERE id=?"
  val deleteSql = "DELETE FROM Exam WHERE id=?"

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
        case "start" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isTimestamp(value)) errors.put(key, ERROR_TIMESTAMP)
        }
        case "end" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isTimestamp(value)) errors.put(key, ERROR_TIMESTAMP)
        }
        case "date" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDatestamp(value)) errors.put(key, ERROR_DATESTAMP)
        }
        case "points" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDouble(value)) errors.put(key, ERROR_DOUBLE)
          else if (value.toDouble < 0 || value.toDouble > 204) errors.put(key, "The value must be in range from 0 to 204")
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
          case "start" => start = value
          case "end" => end = value
          case "date" => date = value
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
    pstmt.setString(2, start)
    pstmt.setString(3, end)
    pstmt.setString(4, date)
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
    pstmt.setString(2, start)
    pstmt.setString(3, end)
    pstmt.setString(4, date)
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
  def fetch(rs: ResultSet): List[Exam] = List()

}

object MutableExam {
  def apply(e: Exam): MutableExam = {
    val mc = new MutableExam
    mc.setId(e.id)
    mc.setDescription(e.description)
    mc.setStart(e.start)
    mc.setEnd(e.end)
    mc.setDate(e.date)
    mc.setPoints(e.points)
    mc
  }
}

class MutableExam {
  val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
  val startProperty: SimpleStringProperty = new SimpleStringProperty()
  val endProperty: SimpleStringProperty = new SimpleStringProperty()
  val dateProperty: SimpleStringProperty = new SimpleStringProperty()
  val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

  def setId(id: Int) = idProperty.set(id)
  def setDescription(description: String) = descriptionProperty.set(description)
  def setStart(start: String) = startProperty.set(start)
  def setEnd(end: String) = endProperty.set(end)
  def setDate(date: String) = dateProperty.set(date)
  def setPoints(points: Double) = pointsProperty.set(points)
}