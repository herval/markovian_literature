package hervalicious.twitter

import scala.concurrent.duration._

class AutoUnfollower(api: Api) extends Runnable {

  override def run = {
    while(true) {
      UnfollowSome(api)

      println("Done unfollowing... For now.")
      Thread.sleep(10.minutes.toMillis)
    }

  }

}
