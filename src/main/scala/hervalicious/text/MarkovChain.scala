package hervalicious.text

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Random

class MarkovChain() {

  type WeightedWords = mutable.HashMap[String, Int]

  private val chain = new mutable.HashMap[String, WeightedWords]()
  private val sentenceOpeners = new mutable.ArrayBuffer[String]()

  // generate random babble (but only full sentences)
  def babble(maxChars: Int, maxSentences: Int, attempts: Int): Option[String] = {
    @tailrec def generate(acc: String = "", sentences: Int = 0): String = {
      (acc, singleBabble()) match {
        case (_, newPhrase) if (acc.length + newPhrase.length) >= maxChars => acc // too long! No likey!
        case (_, _) if sentences >= maxSentences => acc // too many phrases already!
        case (_, newPhrase) => generate(acc + " " + newPhrase, sentences + 1)
      }
    }

    // pick the largest phrase smaller than maxChars
    (1 to attempts).map(_ => generate()).
        filter(_.length <= maxChars).
        sortBy(-_.length).
        headOption
  }

  def reset() = {
    chain.clear()
    sentenceOpeners.clear()
  }

  def singleBabble(opener: String = sentenceOpeners(Random.nextInt(sentenceOpeners.size))): String = {
    wordStream(List(opener)).mkString(" ")
  }

  @tailrec private def wordStream(words: List[String]): List[String] = {
    nextWord(words.last) match {
      case w@Some(word) => wordStream(words ++ w)
      case _ => words
    }
  }

  // load a bunch of text into the chain
  def load(text: String) = {
    @tailrec def load(words: List[String]): Unit = {
      words match {
        case word1 :: word2 :: rest =>
          add(word1, word2)
          if (isClosing(word2)) {
            load(rest) // word2 is a stop word, don't record word2 -> next as a sequence
          } else {
            load(word2 :: rest)
          }
        case _ => // done.
      }
    }

    load(scrub(text))

    this
  }

  // remove chars we don't want to see in the generated babble
  private def scrub(input: String): List[String] = {
    input.replaceAll("\\(|\\)|\n|\"", " ").
        split(" ").
        map(_.trim).
        filterNot(_.isEmpty).
        toList
  }

  // append a word as probable next to the given word or make it a sentence opener
  private def add(word: String, nextWord: String) = {
    if (word.head.isUpper && sentenceOpeners.indexOf(word) == -1) {
      sentenceOpeners.append(word)
    }
    chainWord(word, nextWord)
  }

  private val nonStop = Set(
    "Mr.", "Mrs.", "St.", "Ms."
  )

  private def isClosing(word: String) = {
    !nonStop.contains(word) && (
        word.indexOf(".") >= 0 ||
            word.indexOf("?") >= 0 ||
            word.indexOf("!") >= 0) // TODO regex
  }

  // append nextWord to the list of possible words after the given word
  private def chainWord(word: String, nextWord: String): Option[WeightedWords] = {
    val words = chain.getOrElse(word, new WeightedWords())
    val count = words.getOrElse(nextWord, 1)

    words.put(nextWord, count + 1)
    chain.put(word, words)
  }

  // gets a random word after the given one
  private def nextWord(word: String): Option[String] = {
    if (isClosing(word)) {
      None
    } else {
      chain.get(word) match {
        case Some(nextWords) =>
          val totalWeights = nextWords.values.sum
          val expected = Random.nextInt(totalWeights) + 1

          var i = 0
          nextWords.find {
            case (w: String, q: Int) =>
              i += q
              i >= expected
          }.map {
            case (k, v) => k
          }

        case _ => None
      }
    }
  }

}
