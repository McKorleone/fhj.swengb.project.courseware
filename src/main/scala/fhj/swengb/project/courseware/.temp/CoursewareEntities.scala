/*package fhj.swengb.project.courseware

import java.sql.{ResultSet, Connection, Statement}
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty, SimpleIntegerProperty}
import fhj.swengb.project.courseware.CoursewareAdditional.CoursewareDbTrait
import CoursewareDbTrait.DbEntity
import scala.collection.mutable.ListBuffer





/*
Student
Lecturer
Course +
Lecture
Homework
Project
Exam
Points

 */
object CoursewareEntities {

  //####################################################################################################
  //####################################################################################################
  /*/ Course


  object Course extends DbEntity[Course]{

    val dropTableSql = "drop table if exists Course"
    val createTableSql = "create table Course (id Int, description String, semester String, start String, end String, points Double)"
    val insertSql = "insert into Course (id, description, semester, start, end, points) VALUES (?, ?, ?, ?, ?, ?)"
    val readAllSql = "select * FROM Course"
    val readRowSql = "select * from Course where id = ?"

    val deleteSql1 = "delete from Course where id = ?"
    val updateSql1 = "UPDATE users SET  (description, semester, start, end, points) = (?,?,?,?,?) WHERE (id) = ? "

    // hier wird neue Tabelle anstatt der alte Tabelle erstellt
    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Course.dropTableSql)
      stmt.executeUpdate(Course.createTableSql)
    }
    //hier werden die Objekte in datenbank inserted
    def toDb(conn: Connection)(c: Course): Int = {
      val pstmt = conn.prepareStatement(insertSql)
      pstmt.setInt(1, c.id)
      pstmt.setString(2, c.description)
      pstmt.setString(3, c.semester)
      pstmt.setString(4, c.start)
      pstmt.setString(5, c.end)
      pstmt.setDouble(6, c.points)
      pstmt.executeUpdate()

    }
    //hier werden alle Entities aus der Tabelle genommen und in eine Liste gespeichert, zeile nach der Zeile
    def fromDb(rs: ResultSet): List[Course] = {
      val lb: ListBuffer[Course] = new ListBuffer[Course]()
      while (rs.next()) lb.append(Course(rs.getInt("id"), rs.getString("description"),rs.getString("semester"),rs.getString("start"),rs.getString("end"), rs.getDouble("points")))
      lb.toList
    }
    //hier werden alle Zeilen aus der DB ausgelesen
    def queryAll(con: Connection): ResultSet =
      query(con)(readAllSql)

    override def deleteSql:String = ???

    override def updateSql:String = ???


  }

  case class Course(id: Int, description: String, semester: String, start: String, end: String, points: Double) extends DbEntity[Course] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: Course): Int = 0

    def fromDb(rs: ResultSet): List[Course] = List()

    def dropTableSql: String = "drop table if exists Course"

    def createTableSql: String = "create table Course (id Integer, description String, semester String, start String, end String, points Double)"

    def insertSql: String = "insert into Course (id, description, semester, start, end, points) VALUES (?, ?, ?, ?, ?, ?)"

    def deleteSql:String = "delete from Course where id = ?"

    def updateSql:String = "UPDATE users SET  (description, semester, start, end, points) = (?,?,?,?,?) WHERE (id) = ?"
  }


  // Zwischentabelle für die Ausgabe
  class MutableCourse {

    val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
    val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
    val semesterProperty: SimpleStringProperty = new SimpleStringProperty()
    val startProperty: SimpleStringProperty = new SimpleStringProperty()
    val endProperty: SimpleStringProperty = new SimpleStringProperty()
    val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

    def setId(id: Int) = idProperty.set(id)
    def setDescription(description: String) = descriptionProperty.set(description)
    def setSemester(semester: String) = semesterProperty.set(semester)
    def setStart(start: String) = startProperty.set(start)
    def setEnd(end: String) = endProperty.set(end)
    def setPoints(points: Double) = pointsProperty.set(points)
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
  */
 //##############################################################################################
  /*/####################################################################################################
  Student


  object Student extends DbEntity[Student]{
    val dropTableSql = "drop table if exists Student"
    val createTableSql = "create table Student (id Int, firstName String, secondName String, birthDate String, group Int, year Int, email String)"
    val insertSql = "insert into Location (id, firstName, secondName, birthDate, group, year, email) VALUES (?, ?, ?, ?, ?, ?)"

    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Student.dropTableSql)
      stmt.executeUpdate(Student.createTableSql)
    }

    def toDb(conn: Connection)(st: Student): Int = {
      val pstmt = conn.prepareStatement(insertSql)
      pstmt.setInt(1, st.id)
      pstmt.setString(2, st.firstName)
      pstmt.setString(3, st.secondName)
      pstmt.setString(4, st.birthDate)
      pstmt.setInt(5, st.group)
      pstmt.setInt(6, st.year)
      pstmt.setString(7, st.email)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Student] = {
      val lb: ListBuffer[Student] = new ListBuffer[Student]()
      while (rs.next()) lb.append(Course(rs.getInt("id"), rs.getString("description"),rs.getString("semester"),rs.getString("start"),rs.getString("end"), rs.getDouble("points")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from Course")

  }

  case class Course(id: Int, description: String, semester: String, start: String, end: String, points: Double) extends DbEntity[Location] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: Course): Int = 0

    def fromDb(rs: ResultSet): List[Course] = List()

    def dropTableSql: String = "drop table if exists Course"

    def createTableSql: String = "create table Course (id Integer, description String, semester String, start String, end String, points Double)"

    def insertSql: String = "insert into Course (id, description, semester, start, end, points) VALUES (?, ?, ?, ?, ?, ?)"
  }


  // Zwischentabelle für die Ausgabe
  class MutableCourse {

    val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
    val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
    val semesterProperty: SimpleStringProperty = new SimpleStringProperty()
    val startProperty: SimpleStringProperty = new SimpleStringProperty()
    val endProperty: SimpleStringProperty = new SimpleStringProperty()
    val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

    def setId(id: Int) = idProperty.set(id)
    def setDescription(description: String) = descriptionProperty.set(description)
    def setSemester(semester: String) = semesterProperty.set(semester)
    def setStart(start: String) = startProperty.set(start)
    def setEnd(end: String) = endProperty.set(end)
    def setPoints(points: Double) = pointsProperty.set(points)
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
*/
  //##############################################################################################
  //####################################################################################################
  /*/Lecturer

  object Course extends DbEntity[Course]{
    val dropTableSql = "drop table if exists Course"
    val createTableSql = "create table Location (id Int, description String, semester String, start String, end String, points Double)"
    val insertSql = "insert into Location (lid, locationName, coordinates) VALUES (?, ?, ?, ?, ?, ?)"

    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Course.dropTableSql)
      stmt.executeUpdate(Course.createTableSql)
    }

    def toDb(c: Connection)(l: Course): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, l.id)
      pstmt.setString(2, l.description)
      pstmt.setString(3, l.semester)
      pstmt.setString(4, l.start)
      pstmt.setString(5, l.end)
      pstmt.setDouble(6, l.points)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Course] = {
      val lb: ListBuffer[Course] = new ListBuffer[Course]()
      while (rs.next()) lb.append(Course(rs.getInt("id"), rs.getString("description"),rs.getString("semester"),rs.getString("start"),rs.getString("end"), rs.getDouble("points")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from Course")

  }

  case class Course(id: Int, description: String, semester: String, start: String, end: String, points: Double) extends DbEntity[Course] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: Course): Int = 0

    def fromDb(rs: ResultSet): List[Course] = List()

    def dropTableSql: String = "drop table if exists Course"

    def createTableSql: String = "create table Course (id Integer, description String, semester String, start String, end String, points Double)"

    def insertSql: String = "insert into Course (id, description, semester, start, end, points) VALUES (?, ?, ?, ?, ?, ?)"
  }


  // Zwischentabelle für die Ausgabe
  class MutableCourse {

    val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
    val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
    val semesterProperty: SimpleStringProperty = new SimpleStringProperty()
    val startProperty: SimpleStringProperty = new SimpleStringProperty()
    val endProperty: SimpleStringProperty = new SimpleStringProperty()
    val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

    def setId(id: Int) = idProperty.set(id)
    def setDescription(description: String) = descriptionProperty.set(description)
    def setSemester(semester: String) = semesterProperty.set(semester)
    def setStart(start: String) = startProperty.set(start)
    def setEnd(end: String) = endProperty.set(end)
    def setPoints(points: Double) = pointsProperty.set(points)
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
    */

  //##############################################################################################
  //####################################################################################################
  // Lecture

  /*/Lecturer

  object Course extends DbEntity[Course]{
    val dropTableSql = "drop table if exists Course"
    val createTableSql = "create table Location (id Int, description String, semester String, start String, end String, points Double)"
    val insertSql = "insert into Location (lid, locationName, coordinates) VALUES (?, ?, ?, ?, ?, ?)"

    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Course.dropTableSql)
      stmt.executeUpdate(Course.createTableSql)
    }

    def toDb(c: Connection)(l: Course): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, l.id)
      pstmt.setString(2, l.description)
      pstmt.setString(3, l.semester)
      pstmt.setString(4, l.start)
      pstmt.setString(5, l.end)
      pstmt.setDouble(6, l.points)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Course] = {
      val lb: ListBuffer[Course] = new ListBuffer[Course]()
      while (rs.next()) lb.append(Course(rs.getInt("id"), rs.getString("description"),rs.getString("semester"),rs.getString("start"),rs.getString("end"), rs.getDouble("points")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from Course")

  }

  case class Course(id: Int, description: String, semester: String, start: String, end: String, points: Double) extends DbEntity[Course] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: Course): Int = 0

    def fromDb(rs: ResultSet): List[Course] = List()

    def dropTableSql: String = "drop table if exists Course"

    def createTableSql: String = "create table Course (id Integer, description String, semester String, start String, end String, points Double)"

    def insertSql: String = "insert into Course (id, description, semester, start, end, points) VALUES (?, ?, ?, ?, ?, ?)"
  }


  // Zwischentabelle für die Ausgabe
  class MutableCourse {

    val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
    val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
    val semesterProperty: SimpleStringProperty = new SimpleStringProperty()
    val startProperty: SimpleStringProperty = new SimpleStringProperty()
    val endProperty: SimpleStringProperty = new SimpleStringProperty()
    val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

    def setId(id: Int) = idProperty.set(id)
    def setDescription(description: String) = descriptionProperty.set(description)
    def setSemester(semester: String) = semesterProperty.set(semester)
    def setStart(start: String) = startProperty.set(start)
    def setEnd(end: String) = endProperty.set(end)
    def setPoints(points: Double) = pointsProperty.set(points)
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
    */


  //##############################################################################################
  //####################################################################################################
  // Homework

  /*/Lecturer

  object Course extends DbEntity[Course]{
    val dropTableSql = "drop table if exists Course"
    val createTableSql = "create table Location (id Int, description String, semester String, start String, end String, points Double)"
    val insertSql = "insert into Location (lid, locationName, coordinates) VALUES (?, ?, ?, ?, ?, ?)"

    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Course.dropTableSql)
      stmt.executeUpdate(Course.createTableSql)
    }

    def toDb(c: Connection)(l: Course): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, l.id)
      pstmt.setString(2, l.description)
      pstmt.setString(3, l.semester)
      pstmt.setString(4, l.start)
      pstmt.setString(5, l.end)
      pstmt.setDouble(6, l.points)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Course] = {
      val lb: ListBuffer[Course] = new ListBuffer[Course]()
      while (rs.next()) lb.append(Course(rs.getInt("id"), rs.getString("description"),rs.getString("semester"),rs.getString("start"),rs.getString("end"), rs.getDouble("points")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from Course")

  }

  case class Course(id: Int, description: String, semester: String, start: String, end: String, points: Double) extends DbEntity[Course] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: Course): Int = 0

    def fromDb(rs: ResultSet): List[Course] = List()

    def dropTableSql: String = "drop table if exists Course"

    def createTableSql: String = "create table Course (id Integer, description String, semester String, start String, end String, points Double)"

    def insertSql: String = "insert into Course (id, description, semester, start, end, points) VALUES (?, ?, ?, ?, ?, ?)"
  }


  // Zwischentabelle für die Ausgabe
  class MutableCourse {

    val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
    val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
    val semesterProperty: SimpleStringProperty = new SimpleStringProperty()
    val startProperty: SimpleStringProperty = new SimpleStringProperty()
    val endProperty: SimpleStringProperty = new SimpleStringProperty()
    val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

    def setId(id: Int) = idProperty.set(id)
    def setDescription(description: String) = descriptionProperty.set(description)
    def setSemester(semester: String) = semesterProperty.set(semester)
    def setStart(start: String) = startProperty.set(start)
    def setEnd(end: String) = endProperty.set(end)
    def setPoints(points: Double) = pointsProperty.set(points)
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
    */


  //##############################################################################################
  //####################################################################################################
  // Project

  /*/Lecturer

  object Course extends DbEntity[Course]{
    val dropTableSql = "drop table if exists Course"
    val createTableSql = "create table Location (id Int, description String, semester String, start String, end String, points Double)"
    val insertSql = "insert into Location (lid, locationName, coordinates) VALUES (?, ?, ?, ?, ?, ?)"

    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Course.dropTableSql)
      stmt.executeUpdate(Course.createTableSql)
    }

    def toDb(c: Connection)(l: Course): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, l.id)
      pstmt.setString(2, l.description)
      pstmt.setString(3, l.semester)
      pstmt.setString(4, l.start)
      pstmt.setString(5, l.end)
      pstmt.setDouble(6, l.points)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Course] = {
      val lb: ListBuffer[Course] = new ListBuffer[Course]()
      while (rs.next()) lb.append(Course(rs.getInt("id"), rs.getString("description"),rs.getString("semester"),rs.getString("start"),rs.getString("end"), rs.getDouble("points")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from Course")

  }

  case class Course(id: Int, description: String, semester: String, start: String, end: String, points: Double) extends DbEntity[Course] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: Course): Int = 0

    def fromDb(rs: ResultSet): List[Course] = List()

    def dropTableSql: String = "drop table if exists Course"

    def createTableSql: String = "create table Course (id Integer, description String, semester String, start String, end String, points Double)"

    def insertSql: String = "insert into Course (id, description, semester, start, end, points) VALUES (?, ?, ?, ?, ?, ?)"
  }


  // Zwischentabelle für die Ausgabe
  class MutableCourse {

    val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
    val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
    val semesterProperty: SimpleStringProperty = new SimpleStringProperty()
    val startProperty: SimpleStringProperty = new SimpleStringProperty()
    val endProperty: SimpleStringProperty = new SimpleStringProperty()
    val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

    def setId(id: Int) = idProperty.set(id)
    def setDescription(description: String) = descriptionProperty.set(description)
    def setSemester(semester: String) = semesterProperty.set(semester)
    def setStart(start: String) = startProperty.set(start)
    def setEnd(end: String) = endProperty.set(end)
    def setPoints(points: Double) = pointsProperty.set(points)
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
    */

  //##############################################################################################
  //####################################################################################################
  // Exam

  object Exam extends DbEntity[Exam]{
    val dropTableSql = "drop table if exists Exam"
    val createTableSql = "create table Exam (id Int, description String, start String, end String, date String, points Double)"
    val insertSql = "insert into Exam (id, description, start, end, date, points) VALUES (?, ?, ?, ?, ?, ?)"

    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Exam.dropTableSql)
      stmt.executeUpdate(Exam.createTableSql)
    }

    def toDb(c: Connection)(e: Exam): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, e.id)
      pstmt.setString(2, e.description)
      pstmt.setString(3, e.start)
      pstmt.setString(4, e.end)
      pstmt.setString(5, e.date)
      pstmt.setDouble(6, e.points)
      pstmt.executeUpdate()

    }

    def fromDb(rs: ResultSet): List[Exam] = {
      val lb: ListBuffer[Exam] = new ListBuffer[Exam]()
      while (rs.next()) lb.append(Exam(rs.getInt("id"), rs.getString("description"),rs.getString("start"),rs.getString("end"),rs.getString("date"), rs.getDouble("points")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from Exam")

    def deleteSql:String = ???

    def updateSql:String = ???

  }

  case class Exam(id: Int, description: String,  start: String, end: String, date: String, points: Double) extends DbEntity[Exam] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: Exam): Int = 0

    def fromDb(rs: ResultSet): List[Exam] = List()

    def dropTableSql: String = "drop table if exists Exam"

    def createTableSql: String = "create table Exam (id Int, description String, start String, end String, date String, points Double)"

    def insertSql: String = "insert into Exam (id, description, start, end, date, points) VALUES (?, ?, ?, ?, ?, ?)"

    def deleteSql:String = ???
    def updateSql:String = ???
  }


  // Zwischentabelle für die Ausgabe
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
    def setDate(semester: String) = dateProperty.set(semester)
    def setPoints(points: Double) = pointsProperty.set(points)
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

  //##############################################################################################
  //####################################################################################################
  // Points

  /*/Lecturer

  object Course extends DbEntity[Course]{
    val dropTableSql = "drop table if exists Course"
    val createTableSql = "create table Location (id Int, description String, semester String, start String, end String, points Double)"
    val insertSql = "insert into Location (lid, locationName, coordinates) VALUES (?, ?, ?, ?, ?, ?)"

    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Course.dropTableSql)
      stmt.executeUpdate(Course.createTableSql)
    }

    def toDb(c: Connection)(l: Course): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, l.id)
      pstmt.setString(2, l.description)
      pstmt.setString(3, l.semester)
      pstmt.setString(4, l.start)
      pstmt.setString(5, l.end)
      pstmt.setDouble(6, l.points)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Course] = {
      val lb: ListBuffer[Course] = new ListBuffer[Course]()
      while (rs.next()) lb.append(Course(rs.getInt("id"), rs.getString("description"),rs.getString("semester"),rs.getString("start"),rs.getString("end"), rs.getDouble("points")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from Course")

  }

  case class Course(id: Int, description: String, semester: String, start: String, end: String, points: Double) extends DbEntity[Course] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: Course): Int = 0

    def fromDb(rs: ResultSet): List[Course] = List()

    def dropTableSql: String = "drop table if exists Course"

    def createTableSql: String = "create table Course (id Integer, description String, semester String, start String, end String, points Double)"

    def insertSql: String = "insert into Course (id, description, semester, start, end, points) VALUES (?, ?, ?, ?, ?, ?)"
  }


  // Zwischentabelle für die Ausgabe
  class MutableCourse {

    val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
    val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
    val semesterProperty: SimpleStringProperty = new SimpleStringProperty()
    val startProperty: SimpleStringProperty = new SimpleStringProperty()
    val endProperty: SimpleStringProperty = new SimpleStringProperty()
    val pointsProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

    def setId(id: Int) = idProperty.set(id)
    def setDescription(description: String) = descriptionProperty.set(description)
    def setSemester(semester: String) = semesterProperty.set(semester)
    def setStart(start: String) = startProperty.set(start)
    def setEnd(end: String) = endProperty.set(end)
    def setPoints(points: Double) = pointsProperty.set(points)
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
    */


}
*/