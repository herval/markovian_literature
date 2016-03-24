package hervalicious.twitter.deprecated

import hervalicious.twitter.Api
import twitter4j.TwitterException

object HashtagFollower {

  private val terms = List("#quotes", "#books")

  def apply(api: Api) = {
    try {
      println(s"Following whoever tweets ${terms.mkString(", ")}")
      terms.foreach {
        q =>
          val tweets = api.search(q)
          val users = tweets.map(_.getUser.getId).take(2)
          println(s"Following: ${users}")

          users.map {
            u => api.follow(u)
          }
      }
    } catch {
      case e: TwitterException if e.getErrorCode == 88 => // rate limited, do nothing about it
        println("Whoops, too many requests!")
    }
  }
}
