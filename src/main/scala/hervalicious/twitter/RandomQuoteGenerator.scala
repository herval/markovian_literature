package hervalicious.twitter

import hervalicious.text.BookCollection

import scala.concurrent.duration._

class RandomQuoteGenerator(api: Api, books: BookCollection) extends Runnable {

  override def run() = {
    while (true) {
      println(
        books.quote.map(q => api.post(q))
      )
      println("Entering deep slumber for some time...")
      Thread.sleep(60.minutes.toMillis)
    }
  }

}
