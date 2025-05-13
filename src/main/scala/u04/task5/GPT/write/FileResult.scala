package scala.u04.task5.GPT.write

import scala.u04.task5.GPT.write.FileResult

sealed trait FileResult
case class FileContent(data: String) extends FileResult
case class FileError(message: String)  extends FileResult

