package hervalicious.text

// two books squished together, producing pieces of fine Markovian art.
class BookPair(first: BookTitle, second: BookTitle, bookLoader: GutenbergEbookLoader) {

  def quote: Option[String] = {
    val chars = (140 - title.length - tags.length - 12)
    chain.babble(maxChars = chars, maxSentences = 3, attempts = 15).map { words =>
      s"${words}\n\n- ${title}\n\n${tags}"
    }
  }

  private val chain = {
    new MarkovChain()
      .load(bookLoader.apply(first.filename))
      .load(bookLoader.apply(second.filename))
  }

  private val title = first.prefix.replace("$", second.postfix)
  private val tags = s"${first.hashtags} ${second.hashtags}"
}