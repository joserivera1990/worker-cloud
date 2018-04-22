package models

import java.util.UUID

case class Voice(
  voiceId: UUID,
  nameFile: Option[String],
  filevoiceoriginal: Option[String],
  filevoiceprocessed: Option[String],
  firstname: Option[String],
  secondname: Option[String],
  firstLastname: Option[String],
  secondlastName: Option[String],
  email: Option[String],
  voiceStatus: Option[String],
  idcompetition: Option[String],
  observation: Option[String],
  dateUpload: Option[String])