package hervalicious.twitter.deprecated

import hervalicious.twitter.Api
import twitter4j.TwitterException

object FollowMyFollowers {

  def apply(api: Api) = {
    println("Following whoever follows me...")
    try {
      api.followees.take(20).foreach {
        id =>
          val user = api.follow(id)
          println(s"Followed ${user.getScreenName}")
      }
    } catch {
      case e: TwitterException if e.getErrorCode == 88 => // rate limited, do nothing about it
        println("Whoops, too many requests!")
    }
  }

}
