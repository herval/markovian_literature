package hervalicious.text

import org.scalatest.FunSuite

/**
  * Created by herval on 3/24/16.
  */
class MarkovChainSuite extends FunSuite {

  test("produces a chain") {
    val chain = new MarkovChain()
    val phrase = "Hello world without repeated words. This will be ignored."
    chain.load(phrase)

    assert(chain.singleBabble("Hello") == "Hello world without repeated words.")
  }

  test("produces chains") {
    val chain = new MarkovChain()
    val phrase = "Hello world without repeated words. This will NOT be ignored."
    chain.load(phrase)

    val text = chain.babble(maxChars = 100, maxSentences = 2, attempts = 1).get
    assert(text.split("\\.").length == 2)
  }

  test("considers sr/mister/miss/etc as a word instead of stop phrase") {
    val chain = new MarkovChain()
    val phrase = "Hello Mr. Smith."
    chain.load(phrase)

    assert(chain.singleBabble("Hello") == phrase)
  }

}
