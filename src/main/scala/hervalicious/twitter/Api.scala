package hervalicious.twitter

import twitter4j.conf.ConfigurationBuilder
import twitter4j.TwitterFactory

class Api {

  object ApiConfig {
    def consumerKey = System.getenv("TWITTER_CONSUMER_KEY")
    def consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
    def accessToken = System.getenv("TWITTER_ACCESS_TOKEN")
    def accessTokenSecret = System.getenv("TWITTER_ACCESS_TOKEN_SECRET")
  }

  val config = new ConfigurationBuilder()
  config.setDebugEnabled(true)
    .setOAuthConsumerKey(ApiConfig.consumerKey)
    .setOAuthConsumerSecret(ApiConfig.consumerSecret)
    .setOAuthAccessToken(ApiConfig.accessToken)
    .setOAuthAccessTokenSecret(ApiConfig.accessTokenSecret)

  val factory = new TwitterFactory(config.build())
  val twitter = factory.getInstance()


  def post(message: String) = {
    twitter.updateStatus(message)
  }

}
