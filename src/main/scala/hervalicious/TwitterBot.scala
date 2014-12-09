package hervalicious

import hervalicious.twitter.{FollowMyFollowers, RandomQuote, Api}

object TwitterBot extends App {

  val twitterApi = new Api

  println("Starting philosophical bot...")

  new Thread(new RandomQuote(twitterApi)).run()
  new Thread(new FollowMyFollowers(twitterApi)).run()

}