package hervalicious.twitter

import scala.concurrent.duration._
import twitter4j.TwitterException

class FollowMyFollowers(api: Api) extends Runnable {

  override def run = {
    while(true) {
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

      println("Done following... For now.")
      Thread.sleep(10.minutes.toMillis)
    }
  }

}
