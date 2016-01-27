package fhj.swengb.project.courseware.components

object Helpers {

  /**
    * Returns prepeared string for importing to CSV
    *
    * @param str Input string
    * @return Ready string
    */
  def prepareCsvString(str: String): String = {
    var result: String = str
    if (str.contains(";"))
      result = "\"" + result + "\""

    result
  }

}