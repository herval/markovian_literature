package hervalicious

import hervalicious.text.BookCollection
import hervalicious.twitter.{Api, RandomQuoteGenerator}

object TwitterBot extends App {

  val twitterApi = new Api()
  val books = new BookCollection()

  println("Starting philosophical bot...")

  new Thread(new RandomQuoteGenerator(twitterApi, books)).start()

}