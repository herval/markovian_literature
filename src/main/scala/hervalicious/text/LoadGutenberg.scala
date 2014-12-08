package hervalicious

import scala.io.{Codec, Source}
import java.nio.charset.Charset

object LoadGutenberg {
  implicit val codec: Codec = new Codec(Charset.forName("UTF-8"))

  def apply(filename: String): String = {
    val text = loadText(filename)
    stripLicense(text)
  }

  // WAT.
  private def stripLicense(text: String): String = {
    val opening = """\*\*\*[^\*]+\*\*\*""".r
    val start = opening.findFirstMatchIn(text).map(_.after).getOrElse(text)

    val closing = """End of Project Gutenberg's""".r
    closing.findFirstMatchIn(start).map(_.before).getOrElse(start).toString
  }

  private def loadText(filename: String) = {
    Source.fromInputStream(getClass.getResourceAsStream(s"/books/${filename}")).mkString
  }

}