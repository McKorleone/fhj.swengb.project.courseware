package fhj.swengb.project.courseware.models

import java.sql.ResultSet
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty, SimpleIntegerProperty}
import scala.collection.mutable.ListBuffer
import fhj.swengb.project.courseware.components.DbTrait
import fhj.swengb.project.courseware.components.DbTrait.DbEntity
import fhj.swengb.project.courseware.components.Validator

object Project extends DbEntity[Project] {

  val dropTableSql = "DROP TABLE IF EXISTS Project"
  val createTableSql = "CREATE TABLE IF NOT EXISTS Project (nr INTEGER PRIMARY KEY AUTOINCREMENT, name STRING, description STRING, startdate DATE, deadline DATE, points DOUBLE);"
  val selectAllSql = "SELECT * FROM Project"
  val selectSql = "SELECT * FROM Project WHERE nr=?"

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
  def fetch(rs: ResultSet): List[Project] = {
    val lb: ListBuffer[Project] = new ListBuffer[Project]()
    while (rs.next()) lb.append(Project(rs.getInt("nr"), rs.getString("name"), rs.getString("description"), rs.getString("startdate"), rs.getString("deadline"), rs.getDouble("points")))
    lb.toList
  }

  /**
    * Finds all records in Course table
    *
    * @return List of Course
    */
  def findAll(): List[Project] = {
    fetch(query(selectAllSql))
  }

  /**
    * Finds record of Course table by id
    *
    * @param nr Id of Course
    * @return Course
    */
  def findOne(nr: Int): Project = {
    val pstmt = DbTrait.connection.prepareStatement(selectSql)
    pstmt.setInt(1, nr)
    fetch(pstmt.executeQuery())(0)
  }

}

case class Project(var nr: Int = 0, var name: String = "", var description: String = "", var startdate: String = "", var deadline: String = "", var points: Double = 0.0) extends DbEntity[Project] {

  val insertSql = "INSERT INTO Project (name, description, startdate, deadline, points) VALUES (?, ?, ?, ?, ?)"
  val updateSql = "UPDATE Project SET name=?, description=?, startdate=?, deadline=?, points=? WHERE nr=?"
  val deleteSql = "DELETE FROM Project WHERE nr=?"

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
        case "description" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
        }
        case "startdate" => {
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
          case "name" => name = value
          case "description" => description = value
          case "startdate" => startdate = value
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
    pstmt.setString(1, name)
    pstmt.setString(2, description)
    pstmt.setString(3, startdate)
    pstmt.setString(4, deadline)
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
    pstmt.setString(1, name)
    pstmt.setString(2, description)
    pstmt.setString(3, startdate)
    pstmt.setString(4, deadline)
    pstmt.setDouble(5, points)
    pstmt.setInt(6, nr)

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
  def fetch(rs: ResultSet): List[Project] = List()

}

object MutableProject {
  def apply(c: Project): MutableProject = {
    val mc = new MutableProject
    mc.setNr(c.nr)
    mc.setName(c.name)
    mc.setDescription(c.description)
    mc.setStartdate(c.startdate)
    mc.setDeadline(c.deadline)
    mc.setPoints(c.points)
    mc
  }
}

class MutableProject {
  val nrProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val nameProperty: SimpleStringProperty = new SimpleStringProperty()
  val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
  val startdateProperty: SimpleStringProperty = new SimpleStringProperty()
  val deadlineProperty: SimpleStringProperty = new SimpleStringProperty()
  val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

  def setNr(nr: Int) = nrProperty.set(nr)
  def setName(name: String) = nameProperty.set(name)
  def setDescription(description: String) = descriptionProperty.set(description)
  def setStartdate(startdate: String) = startdateProperty.set(startdate)
  def setDeadline(deadline: String) = deadlineProperty.set(deadline)
  def setPoints(points: Double) = pointsProperty.set(points)
}
