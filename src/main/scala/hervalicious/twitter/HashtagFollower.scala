package hervalicious.twitter

import scala.concurrent.duration._
import twitter4j.{Status, TwitterException}

class HashtagFollower(api: Api) extends Runnable {

  private val terms = List("#quotes", "#literature", "#books")

  override def run = {
    while(true) {
      try {
        println(s"Following whoever tweets ${terms.mkString(", ")}")
        terms.foreach {
          q =>
            val tweets = api.search(q)
            val users = tweets.map(_.getUser.getId).take(2)
            println(s"Following: ${users}")

            users.map { u => api.follow(u) }
        }
      } catch {
        case e: TwitterException if e.getErrorCode == 88 => // rate limited, do nothing about it
          println("Whoops, too many requests!")
      }

      println("Done following... For now.")
      Thread.sleep(1.hour.toMillis)
    }
  }

}