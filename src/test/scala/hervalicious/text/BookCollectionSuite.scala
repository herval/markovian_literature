package hervalicious.text

import org.scalatest.FunSuite

/**
  * Created by herval on 3/24/16.
  */
class BookCollectionSuite extends FunSuite {

  val book = BookTitle("The Adventures of Tom Sawyer and $", "Tom Sawyer", "tom_sawyer.txt", "#TomSawyer #Twain")

  val collection = new BookCollection(
    availableBooks = List(book, book)
  )

  test("loads from txt files") {
    println(collection.quote.get)
    assert(collection.quote.isDefined)
  }

}
