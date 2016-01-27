package fhj.swengb.project.courseware.models

import java.sql.ResultSet
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty, SimpleIntegerProperty}
import scala.collection.mutable.ListBuffer
import fhj.swengb.project.courseware.components.DbTrait
import fhj.swengb.project.courseware.components.DbTrait.DbEntity
import fhj.swengb.project.courseware.components.Validator

object Lecture extends DbEntity[Lecture] {

  val dropTableSql = "DROP TABLE IF EXISTS Lecture"
  val createTableSql = "CREATE TABLE IF NOT EXISTS Lecture (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, start STRING, \"end\" STRING, date DATE, room INTEGER, lecturer STRING, points DOUBLE)"
  val selectAllSql = "SELECT * FROM Lecture"
  val selectSql = "SELECT * FROM Lecture WHERE id=?"

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
  def fetch(rs: ResultSet): List[Lecture] = {
    val lb: ListBuffer[Lecture] = new ListBuffer[Lecture]()
    while (rs.next()) lb.append(Lecture(rs.getInt("id"), rs.getString("name"), rs.getString("start"), rs.getString("end"), rs.getString("date"), rs.getInt("room"), rs.getString("lecturer"), rs.getDouble("points")))
    lb.toList
  }

  /**
    * Finds all records in Lecture table
    *
    * @return List of Lecture
    */
  def findAll(): List[Lecture] = {
    fetch(query(selectAllSql))
  }

  /**
    * Finds record of Lecture table by id
    *
    * @param id Id of Lecture
    * @return Lecture
    */
  def findOne(id: Int): Lecture = {
    val pstmt = DbTrait.connection.prepareStatement(selectSql)
    pstmt.setInt(1, id)
    fetch(pstmt.executeQuery())(0)
  }

}

case class Lecture(id: Int = 0, var name: String = "", var start: String = "", var end: String = "", var date: String = "", var room: Int = 0, var lecturer: String = "", var points: Double = 0.0) extends DbEntity[Lecture] {

  val insertSql = "INSERT INTO Lecture (name, start, end, date, room, lecturer, points) VALUES (?, ?, ?, ?, ?, ?, ?)"
  val updateSql = "UPDATE Lecture SET name=?, start=?, end=?, date=?, room=?, lecturer=?, points=? WHERE id=?"
  val deleteSql = "DELETE FROM Lecture WHERE id=?"

  /**
    * @inheritdoc
    */
  def validate(): Boolean = {

    // validating
    for(attribute <- attributes) {
      val key = attribute._1
      val value = attribute._2

      key match {
        case "name" => {
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
        case "room" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isInteger(value)) errors.put(key, ERROR_INTEGER)
        }
        case "lecturer" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
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
          case "name" => name = value
          case "start" => start = value
          case "end" => end = value
          case "date" => date = value
          case "room" => room = value.toInt
          case "lecturer" => lecturer = value
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
    pstmt.setString(1, name)
    pstmt.setString(2, start)
    pstmt.setString(3, end)
    pstmt.setString(4, date)
    pstmt.setInt(5, room)
    pstmt.setString(6, lecturer)
    pstmt.setDouble(7, points)

    pstmt.executeUpdate() == 1
  }

  /**
    * @inheritdoc
    */
  def update(): Boolean = {
    if (!validate())
      return false

    val pstmt = DbTrait.connection.prepareStatement(updateSql)
    pstmt.setString(1, name)
    pstmt.setString(2, start)
    pstmt.setString(3, end)
    pstmt.setString(4, date)
    pstmt.setInt(5, room)
    pstmt.setString(6, lecturer)
    pstmt.setDouble(7, points)
    pstmt.setInt(8, id)

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
  def fetch(rs: ResultSet): List[Lecture] = List()

}

object MutableLecture {
  def apply(c: Lecture): MutableLecture = {
    val mc = new MutableLecture
    mc.setId(c.id)
    mc.setName(c.name)
    mc.setStart(c.start)
    mc.setEnd(c.end)
    mc.setDate(c.date)
    mc.setRoom(c.room)
    mc.setLecturer(c.lecturer)
    mc.setPoints(c.points)
    mc
  }
}

class MutableLecture {
  val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val nameProperty: SimpleStringProperty = new SimpleStringProperty()
  val startProperty: SimpleStringProperty = new SimpleStringProperty()
  val endProperty: SimpleStringProperty = new SimpleStringProperty()
  val roomProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val dateProperty: SimpleStringProperty = new SimpleStringProperty()
  val lecturerProperty: SimpleStringProperty = new SimpleStringProperty()
  val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

  def setId(id: Int) = idProperty.set(id)
  def setName(name: String) = nameProperty.set(name)
  def setStart(start: String) = startProperty.set(start)
  def setEnd(end: String) = endProperty.set(end)
  def setDate(date: String) = dateProperty.set(date)
  def setRoom(room: Int) = roomProperty.set(room)
  def setLecturer(lecturer: String) = lecturerProperty.set(lecturer)
  def setPoints(points: Double) = pointsProperty.set(points)
}