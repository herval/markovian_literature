package hervalicious

// two books squished together, producing pieces of fine Markovian art.
class BookMishMash(first: BookTitle, second: BookTitle) {

  def quote = {
    s"${chain.babble(2)}\n\n- ${title}"
  }

  private val chain = {
    new MarkovChain()
      .load(LoadGutenberg(first.filename))
      .load(LoadGutenberg(second.filename))
  }

  private def title = first.prefix.replace("$", second.postfix)

}