package hervalicious

import scala.util.Random

class Librarian {

  def quote = {
    randomBookCombo.quote
  }

  private val availableBooks = List(
    BookTitle("Alice in $",  "Wonderland", "pg11.txt"),
    BookTitle("Peter Pan in $", "Peter Pan", "pg16.txt"),
    BookTitle("A Christmas Carol for $",  "A Ghost Story", "pg46.txt"),
    BookTitle("The Adventures of Tom Sawyer and $",  "Tom Sawyer", "pg74.txt"),
    BookTitle("$'s Frankenstein",  "Frankenstein", "pg84.txt"),
    BookTitle("The Picture of $",  "Dorian Gray", "pg174.txt"),
    BookTitle("Pride, Prejudice and $", "Pride and Prejudice", "pg1342.txt"),
    BookTitle("The Adventures of $", "Sherlock Holmes", "pg1661.txt"),
    BookTitle("Grimm's $ Tales", "Brothers Grimm", "pg2591.txt")
  )

  private def randomBookCombo: BookMishMash = {
    val books = Random.shuffle(availableBooks).take(2)
    new BookMishMash(books(0), books(1))
  }

}
