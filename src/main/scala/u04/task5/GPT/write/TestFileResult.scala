package scala.u04.task5.GPT.write

@main
def testFileResult(): Unit =

  def readFile(path: String): FileResult =
    if (path.endsWith(".txt")) FileContent(s"Contents of $path")
    else FileError(s"Unsupported file type: $path")

  // Pattern match
  readFile("doc.txt") match {
    case FileContent(data) => println(s"Got data: $data")
    case FileError(err)    => println(s"Error: $err")
  }

  def parse(data: String): FileResult =
    if (data.nonEmpty) FileContent(data.split("\n").mkString(";"))
    else FileError("Empty file")

  def process(lines: String): FileResult =
    if (lines.contains(";")) FileContent(lines.toUpperCase)
    else FileError("No line breaks found")

//  val result: FileResult = for {
//    content <- readFile("doc.txt")
//    parsed <- parse(content.data)
//    processed <- process(parsed)
//  } yield processed

// result will be FileContent(...) or FileError(...)
