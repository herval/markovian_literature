package hervalicious

import hervalicious.twitter.{HashtagFollower, FollowMyFollowers, RandomQuote, Api}

object TwitterBot extends App {

  val twitterApi = new Api

  println("Starting philosophical bot...")

  new Thread(new RandomQuote(twitterApi)).start()
  new Thread(new FollowMyFollowers(twitterApi)).start()

}