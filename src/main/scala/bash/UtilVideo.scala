package bash

object UtilVideo {

  val MP3 = ".mp3"
  val FORMAT_MP3 = "mp3"

  def getNameFile(path: String): String = {
    val name = path.substring(path.lastIndexOf("/") + 1)
    val onlyName = name.substring(0, name.lastIndexOf("."))
    onlyName + MP3
  }

  def getNameFileFromName(nameFile: String): String = {
    nameFile.replace(getFormatFile(nameFile), FORMAT_MP3)
  }

  def getFormatFile(path: String) =
    path.substring(path.lastIndexOf(".") + 1)

  def getFolderToSave(path: String): String = {
    val firstLetter = getNameFile(path).substring(0, 1)
    firstLetter match {
      case name if (name.matches("[a-zA-Z].*")) => firstLetter.toLowerCase
      case _ => "other"
    }
  }
}
