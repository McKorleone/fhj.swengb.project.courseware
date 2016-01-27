package fhj.swengb.project.courseware.components

object Validator {

  def isDatestamp(value: String): Boolean = {
    value.matches("""^(\d{4})\-(0?[1-9]|1[012])\-(0?[1-9]|[12][0-9]|3[01])$""")
  }

  def isTimestamp(value: String): Boolean = {
    value.matches("""^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$""")
  }

  def isDouble(value: String): Boolean = {
    try {
      value.toDouble
    } catch {
      case _ => return false
    }

    true
  }

  def isInteger(value: String): Boolean = {
    try {
      value.toInt
    } catch {
      case _ => return false
    }

    true
  }

}