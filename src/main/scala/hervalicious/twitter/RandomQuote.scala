package hervalicious.twitter

import hervalicious.Librarian
import scala.concurrent.duration._

class RandomQuote(api: Api) extends Runnable {

  val librarian = new Librarian

  override def run = {
    while(true) {
      api.post(librarian.quote)
      println("Entering deep slumber for some time...")
      Thread.sleep(30.minutes.toMillis)
    }
  }

}
