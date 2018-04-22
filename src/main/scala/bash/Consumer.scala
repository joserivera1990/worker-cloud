package bash

import aws.s3.ConexionS3
import models.Voice
import play.api.libs.json._
import play.api.libs.json.JsValue

import scala.collection.JavaConverters._
import aws.sqs.ConexionSQS

object Consumer extends App {
  println("start consumer App")
  val conexionSQS = new ConexionSQS
  val sqsClient = conexionSQS.conexionSQS()
  var conexionS3 = new ConexionS3();
  var s3Client = conexionS3.createClientS3();
  implicit val fileVoice = Json.format[Voice]
  println("start consumer")
  while (true) {
    val messages = conexionSQS.receiveMessage(sqsClient)
    println(messages)
    for (record <- messages.asScala) {
      val json: JsValue = Json.parse(record.getBody())
      ProcessSubscribe.processVoice(json.as[Voice], conexionS3, s3Client)
    }
    conexionSQS.deleteAllMessage(messages, sqsClient)
    Thread.sleep(300)
  }
}
