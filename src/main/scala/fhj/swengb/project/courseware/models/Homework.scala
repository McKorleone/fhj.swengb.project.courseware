package fhj.swengb.project.courseware.models


import java.sql.ResultSet
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty, SimpleIntegerProperty}
import scala.collection.mutable.ListBuffer
import fhj.swengb.project.courseware.components.DbTrait
import fhj.swengb.project.courseware.components.DbTrait.DbEntity
import fhj.swengb.project.courseware.components.Validator

object Homework extends DbEntity[Homework] {

  val dropTableSql = "DROP TABLE IF EXISTS Homework"
  val createTableSql = "CREATE TABLE IF NOT EXISTS Homework (nr INTEGER PRIMARY KEY AUTOINCREMENT, description STRING, date DATE, deadline DATE, points DOUBLE);"
  val selectAllSql = "SELECT * FROM Homework"
  val selectSql = "SELECT * FROM Homework WHERE nr=?"

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
  def fetch(rs: ResultSet): List[Homework] = {
    val lb: ListBuffer[Homework] = new ListBuffer[Homework]()
    while (rs.next()) lb.append(Homework(rs.getInt("nr"), rs.getString("description"), rs.getString("date"), rs.getString("deadline"), rs.getDouble("points")))
    lb.toList
  }

  /**
    * Finds all records in Course table
    *
    * @return List of Course
    */
  def findAll(): List[Homework] = {
    fetch(query(selectAllSql))
  }

  /**
    * Finds record of Course table by id
    *
    * @param id Id of Course
    * @return Course
    */
  def findOne(id: Int): Homework = {
    val pstmt = DbTrait.connection.prepareStatement(selectSql)
    pstmt.setInt(1, id)
    fetch(pstmt.executeQuery())(0)
  }

}

case class Homework(var nr: Int = 0, var description: String = "", var date: String = "", var deadline: String = "", var points: Double = 0.0) extends DbEntity[Homework] {

  val insertSql = "INSERT INTO Homework (description, date, deadline, points) VALUES (?, ?, ?, ?)"
  val updateSql = "UPDATE Homework SET description=?, date=?, deadline=?, points=? WHERE nr=?"
  val deleteSql = "DELETE FROM Homework WHERE nr=?"

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
        case "date" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDatestamp(value)) errors.put(key, ERROR_DATESTAMP)
        }
        case "deadline" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDatestamp(value)) errors.put(key, ERROR_DATESTAMP)
        }
        case "points" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDouble(value)) errors.put(key, ERROR_DOUBLE)
          else if (value.toDouble < 0 || value.toDouble > 204) errors.put(key, "The Value must be in range from 0 to 204")
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
          case "date" => date = value
          case "deadline" => deadline = value
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
    pstmt.setString(2, date)
    pstmt.setString(3, deadline)
    pstmt.setDouble(4, points)

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
    pstmt.setString(2, date)
    pstmt.setString(3, deadline)
    pstmt.setDouble(4, points)
    pstmt.setInt(5, nr)

    pstmt.executeUpdate() == 1
  }

  /**
    * @inheritdoc
    */
  def delete(): Boolean = {
    val pstmt = DbTrait.connection.prepareStatement(deleteSql)
    pstmt.setInt(1, nr)

    pstmt.executeUpdate() == 1
  }

  /**
    * @inheritdoc
    */
  def fetch(rs: ResultSet): List[Homework] = List()

}

object MutableHomework {
  def apply(c: Homework): MutableHomework = {
    val mc = new MutableHomework
    mc.setNr(c.nr)
    mc.setDescription(c.description)
    mc.setDate(c.date)
    mc.setDeadline(c.deadline)
    mc.setPoints(c.points)
    mc
  }
}

class MutableHomework {
  val nrProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
  val dateProperty: SimpleStringProperty = new SimpleStringProperty()
  val deadlineProperty: SimpleStringProperty = new SimpleStringProperty()
  val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

  def setNr(nr: Int) = nrProperty.set(nr)
  def setDescription(description: String) = descriptionProperty.set(description)
  def setDate(date: String) = dateProperty.set(date)
  def setDeadline(deadline: String) = deadlineProperty.set(deadline)
  def setPoints(points: Double) = pointsProperty.set(points)
}