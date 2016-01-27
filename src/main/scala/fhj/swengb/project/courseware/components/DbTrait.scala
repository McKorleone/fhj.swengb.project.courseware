package fhj.swengb.project.courseware.components

import scala.collection.mutable.Map
import java.sql.{Connection, DriverManager, ResultSet}

  /**
    * Example to connect to a database.
    *
    * Initializes the database, inserts example data and reads it again.
    *
    */
  object DbTrait {

    /**
      * A marker interface for datastructures which should be persisted to a jdbc database.
      *
      * @tparam T the type to be persisted / loaded
      */
    trait DbEntity[T] {

      /**
        * Constants
        */

      final val ERROR_REQUIRED = "Field Required"
      final val ERROR_INTEGER = "Value must be integer"
      final val ERROR_DOUBLE = "Value must be double"
      final val ERROR_DATESTAMP = "Value must be datestamp in format yyyy-mm-dd"
      final val ERROR_TIMESTAMP = "Value must be timestamp in format hh:mm"

      var attributes: Map[String, String] = Map()
      var errors: Map[String, String] = Map()

      def validate(): Boolean

      def hasErrors(): Boolean = errors.size > 0

      def hasError(attribute: String): Boolean = errors.exists(_._1 == attribute)

      //def getError(attribute: String): Map = errors.get(attribute)

      /**
        * Creates the table this entity is stored in
        *
        * @return Result of operation
        */
      def createTable(): Boolean

      /**
        * Inserts given type to the database.
        *
        * @return Result of operation
        */
      def create(): Boolean

      /**
        * Updates given type in the database.
        *
        * @return Result of operation
        */
      def update(): Boolean

      /**
        * Deletes given type in the database.
        *
        * @return Result of operation
        */
      def delete(): Boolean

      /**
        * Given the ResultSet, it fetches its rows and converts them into instances of T
        *
        * @param rs
        * @return
        */
      def fetch(rs: ResultSet): List[T]

      /**
        * Queries the database
        *
        * @param query SQL Query
        * @return ResultSet
        */
      def query(query: String): ResultSet = DbTrait.connection.createStatement().executeQuery(query)

    }

    lazy val connection: Connection = DriverManager.getConnection("jdbc:sqlite:courseware.db")
}




