package hervalicious

import scala.io.Source

object LoadGutenberg {

  def apply(filename: String, basePath: String = "src/main/resources"): String = {
    val text = loadText(filename, basePath)
    stripLicense(text)
  }

  // WAT.
  private def stripLicense(text: String): String = {
    val opening = """\*\*\*[^\*]+\*\*\*""".r
    val start = opening.findFirstMatchIn(text).map(_.after).getOrElse(text)

    val closing = """End of Project Gutenberg's""".r
    closing.findFirstMatchIn(start).map(_.before).getOrElse(start).toString
  }

  private def loadText(filename: String, basePath: String) = {
    Source.fromFile(s"${basePath}/${filename}").mkString
  }

}