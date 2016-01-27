package fhj.swengb.project.courseware.models

import java.sql.ResultSet
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty, SimpleIntegerProperty}
import scala.collection.mutable.ListBuffer
import fhj.swengb.project.courseware.components.DbTrait
import fhj.swengb.project.courseware.components.DbTrait.DbEntity
import fhj.swengb.project.courseware.components.Validator

object Student extends DbEntity[Student] {

  val dropTableSql = "DROP TABLE IF EXISTS Student"
  val createTableSql = "CREATE TABLE IF NOT EXISTS Student (id INTEGER PRIMARY KEY AUTOINCREMENT, first_name STRING, last_name STRING, \"group\" INTEGER, points DOUBLE, percentage INTEGER, grade INTEGER)"
  val selectAllSql = "SELECT * FROM Student"
  val selectSql = "SELECT * FROM Student WHERE id=?"

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
  def fetch(rs: ResultSet): List[Student] = {
    val lb: ListBuffer[Student] = new ListBuffer[Student]()
      while (rs.next()) lb.append(Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getInt("group"), rs.getDouble("points"), rs.getInt("percentage"), rs.getInt("grade")))
    lb.toList
  }

  /**
    * Finds all records in Student table
    *
    * @return List of Student
    */
  def findAll(): List[Student] = {
    fetch(query(selectAllSql))
  }

  /**
    * Finds record of Student table by id
    *
    * @param id Id of Student
    * @return Student
    */
  def findOne(id: Int): Student = {
    val pstmt = DbTrait.connection.prepareStatement(selectSql)
    pstmt.setInt(1, id)
    fetch(pstmt.executeQuery())(0)
  }

}

case class Student(id: Int = 0, var first_name: String = "", var last_name: String = "", var group: Int = 0, var points: Double = 0.0, var percentage: Int = 0, var grade: Int = 0) extends DbEntity[Student] {

  val insertSql = "INSERT INTO Student (first_name, last_name, \"group\", points, percentage, grade) VALUES (?, ?, ?, ?, ?, ?)"
  val updateSql = "UPDATE Student SET first_name=?, last_name=?, \"group\"=?, points=?, percentage=?, grade=? WHERE id=?"
  val deleteSql = "DELETE FROM Student WHERE id=?"

  /**
    * @inheritdoc
    */
  def validate(): Boolean = {

    // validating
    for(attribute <- attributes) {
      val key = attribute._1
      val value = attribute._2

      key match {
        case "first_name" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
        }
        case "last_name" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
        }
        case "group" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isInteger(value)) errors.put(key, ERROR_INTEGER)
          else if (value.toInt < 1 || value.toInt > 3) errors.put(key, "The value must be in range from 1 to 3")
        }
        case "points" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isDouble(value)) errors.put(key, ERROR_DOUBLE)
          else if (value.toDouble < 0 || value.toDouble > 204) errors.put(key, "The value must be in range from 0 to 204")
        }
        case "percentage" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isInteger(value)) errors.put(key, ERROR_INTEGER)
          else if (value.toInt < 0 || value.toInt > 100) errors.put(key, "The value must be in range from 0 to 100")
        }
        case "grade" => {
          if (value == "") errors.put(key, ERROR_REQUIRED)
          else if (!Validator.isInteger(value)) errors.put(key, ERROR_INTEGER)
          else if (value.toInt < 1 || value.toInt > 5) errors.put(key, "The value must be in range from 1 to 5")
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
          case "first_name" => first_name = value
          case "last_name" => last_name = value
          case "group" => group = value.toInt
          case "points" => points = value.toDouble
          case "percentage" => percentage = value.toInt
          case "grade" => grade = value.toInt
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
    pstmt.setString(1, first_name)
    pstmt.setString(2, last_name)
    pstmt.setInt(3, group)
    pstmt.setDouble(4, points)
    pstmt.setInt(5, percentage)
    pstmt.setInt(6, grade)

    pstmt.executeUpdate() == 1
  }

  /**
    * @inheritdoc
    */
  def update(): Boolean = {
    if (!validate())
      return false

    val pstmt = DbTrait.connection.prepareStatement(updateSql)
    pstmt.setString(1, first_name)
    pstmt.setString(2, last_name)
    pstmt.setInt(3, group)
    pstmt.setDouble(4, points)
    pstmt.setInt(5, percentage)
    pstmt.setInt(6, grade)
    pstmt.setInt(7, id)

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
  def fetch(rs: ResultSet): List[Student] = List()

}

object MutableStudent {
  def apply(c: Student): MutableStudent = {
    val mc = new MutableStudent
    mc.setId(c.id)
    mc.setFirstName(c.first_name)
    mc.setLastName(c.last_name)
    mc.setGroup(c.group)
    mc.setPoints(c.points)
    mc.setPercentage(c.percentage)
    mc.setGrade(c.grade)
    mc
  }
}

class MutableStudent {
  val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val firstNameProperty: SimpleStringProperty = new SimpleStringProperty()
  val lastNameProperty: SimpleStringProperty = new SimpleStringProperty()
  val groupProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()
  val percentageProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val gradeProperty: SimpleIntegerProperty = new SimpleIntegerProperty()

  def setId(id: Int) = idProperty.set(id)
  def setFirstName(fn: String) = firstNameProperty.set(fn)
  def setLastName(ln: String) = lastNameProperty.set(ln)
  def setGroup(group: Int) = groupProperty.set(group)
  def setPoints(points: Double) = pointsProperty.set(points)
  def setPercentage(percentage: Int) = percentageProperty.set(percentage)
  def setGrade(grade: Int) = gradeProperty.set(grade)
}