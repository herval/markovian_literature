package hervalicious.twitter

import scala.concurrent.duration._

class AutoFollower(api: Api) extends Runnable {

  override def run = {
    while(true) {
      FollowMyFollowers(api)
      HashtagFollower(api)

      println("Done following... For now.")
      Thread.sleep(10.minutes.toMillis)
    }

  }

}
