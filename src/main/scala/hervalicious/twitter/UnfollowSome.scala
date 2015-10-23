package hervalicious.twitter

import twitter4j.TwitterException

object UnfollowSome {

  def apply(api: Api) = {
    println("Unfollowing some old time folks...")
    try {
      api.following.reverse.take(20).foreach {
        id =>
          val user = api.unfollow(id)
          println(s"Unfollowed ${user.getScreenName}")
      }
    } catch {
      case e: TwitterException if e.getErrorCode == 88 => // rate limited, do nothing about it
        println("Whoops, too many requests!")
    }
  }

}
