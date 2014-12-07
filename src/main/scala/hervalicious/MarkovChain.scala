package hervalicious

import scala.collection.mutable
import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class MarkovChain() {

  type WeightedWords = mutable.HashMap[String, Int]

  private val chain = new mutable.HashMap[String, WeightedWords]()
  private val sentenceOpeners = new mutable.ArrayBuffer[String]()  // TODO set

  // generate random babble (but only full sentences)
  def babble(maxSentences: Int = 2): String = {
    val fullSentences = new ArrayBuffer[String]()

    while(fullSentences.size < maxSentences) {
      val sentence = singleBabble
      if(isClosing(sentence.last)) {
        fullSentences.append(sentence.mkString(" "))
      }
    }

    fullSentences.mkString(" ")
  }

  def singleBabble = {
    val sentence = new ArrayBuffer[String]()
    sentence.append(sentenceOpeners(Random.nextInt(sentenceOpeners.size)))

    var done = false
    while(!done) {
      val prev = sentence.last
      getNext(prev) match {
        case Some(word) =>
          sentence.append(word)
          if(isClosing(word)) {
            done = true
          }
        case None =>
          done = true
      }
    }

    sentence
  }

  // load a bunch of text into the chain
  def load(text: String) = {
    val words = scrub(text)
    words.zipWithIndex.map {
      case (w: String, i: Int) if i < words.size-1 =>
        add(w, words(i + 1))
      case _ => // nuthin.
    }
  }

  // remove chars we don't want to see in the generated babble
  private def scrub(input: String): Array[String] = {
    input.replaceAll("\\(|\\)|\n|\"", " ").split(" ").map(_.trim).filterNot(_.isEmpty)
  }

  // append a word as probable next to the given word or make it a sentence opener
  private def add(word: String, nextWord: String) = {
    if(word.head.isUpper && sentenceOpeners.indexOf(word) == -1) {
      sentenceOpeners.append(word)
    }
    chainWord(word, nextWord)
  }


  private def isClosing(word: String) = {
    word.indexOf(".") >= 0 || word.indexOf("?") >= 0 || word.indexOf("!") >= 0 // TODO regex
  }

  // append nextWord to the list of possible words after the given word
  private def chainWord(word: String, nextWord: String): Option[WeightedWords] = {
    val words = chain.get(word) match {
      case Some(chain: WeightedWords) => chain
      case None => new WeightedWords()
    }

    val count = words.get(nextWord) match {
      case Some(num: Int) => num + 1
      case None => 1
    }

    words.put(nextWord, count)
    chain.put(word, words)
  }

  // gets a random word after the given one
  private def getNext(word: String): Option[String] = {
    chain.get(word) match {
      case Some(nextWords) =>
        val totalWeights = nextWords.values.sum
        val expected = Random.nextInt(totalWeights)+1

        var i = 0
        nextWords.find {
          case(w: String, q: Int) =>
            i += q
            i >= expected
        }.map{
          case(k, v) => k
        }

      case _ => None
    }
  }

}



object Test {
  def main(args: Array[String]) {
    val chain = new MarkovChain()
    chain.load(Source.fromFile("src/main/resources/sherlock.txt").mkString)
    chain.load(Source.fromFile("src/main/resources/alice_wonderland.txt").mkString)

    1 to 10 foreach { _ =>
      println(chain.babble(2))
    }
  }
}
