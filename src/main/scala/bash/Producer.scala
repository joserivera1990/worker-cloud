package bash

import java.util.Properties

import aws.sqs.ConexionSQS
import models.Voice
import play.api.libs.json._

object Producer {

  implicit private val voiceWrite = Json.writes[Voice]

  def publishVoice(voice: Voice): Unit = {
    //Integarcion con SQS
    val conexionSQS = new ConexionSQS
    val sqsClient = conexionSQS.conexionSQS()
    val jsonVoice = Json.toJson(voice)
    val envio = conexionSQS.sendMessage(voice.voiceId.toString, jsonVoice.toString(), sqsClient)
    println("Resultado --> " + envio)
  }
}