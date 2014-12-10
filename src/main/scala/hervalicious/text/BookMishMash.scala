package hervalicious

// two books squished together, producing pieces of fine Markovian art.
class BookMishMash(first: BookTitle, second: BookTitle) {

  def quote = {
    val chars = (140 - title.length - tags.length - 12)
    s"${chain.babble(maxChars = chars, maxSentences = 3, maxAttempts = 15)}\n\n- ${title}\n\n${tags}"
  }

  private val chain = {
    new MarkovChain()
      .load(LoadGutenberg(first.filename))
      .load(LoadGutenberg(second.filename))
  }

  private val title = first.prefix.replace("$", second.postfix)
  private val tags = s"${first.hashtags} ${second.hashtags}"
}