package hervalicious

import hervalicious.twitter.{AutoFollower, RandomQuote, Api}

object TwitterBot extends App {

  val twitterApi = new Api

  println("Starting philosophical bot...")

  new Thread(new RandomQuote(twitterApi)).start()
  new Thread(new AutoFollower(twitterApi)).start()

}