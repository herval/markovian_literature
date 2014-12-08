package hervalicious

import scala.util.Random


object TwitterBot extends App {

  val availableBooks = List(
    BookTitle("Pride, Prejudice and $",  "Pride and Prejudice", "pride_prejudice.txt"),
    BookTitle("Alice in $",  "Wonderland", "alice_wonderland.txt"),
    BookTitle("A Christmas $",  "Carol", "alice_wonderland.txt"),
    BookTitle("$'s Time Machine",  " Time Machine", "alice_wonderland.txt")
  )

  def randomBookCombo: BookMishMash = {
    val books = Random.shuffle(availableBooks).take(2)
    new BookMishMash(books(0), books(1))
  }

  println("Starting bot...")
  println(randomBookCombo.quote)
}