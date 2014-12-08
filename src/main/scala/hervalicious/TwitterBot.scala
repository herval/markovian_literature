package hervalicious

import hervalicious.twitter.Api
import scala.concurrent.duration._

object TwitterBot extends App {

  val librarian = new Librarian
  val twitterApi = new Api

  println("Starting philosophical bot...")

  while(true) {
    twitterApi.post(librarian.quote)
    println("Entering deep slumber for some time...")
    Thread.sleep(30.minutes.toMillis)
  }

}