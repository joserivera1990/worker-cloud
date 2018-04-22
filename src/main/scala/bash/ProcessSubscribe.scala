package bash

import aws.s3.ConexionS3
import com.amazonaws.services.s3.AmazonS3
import com.typesafe.config.ConfigFactory
import models.Voice

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ProcessSubscribe {

  def processVoice(voice: Voice, conexionS3: ConexionS3, s3Client: AmazonS3) = {
    println("voice start" + voice.voiceId)
    downloadVoice(voice)
    val nameFileFinal = UtilVideo.getNameFileFromName(voice.nameFile.get)
    val pathFinalLocal = ConfigFactory.load().getString("audio.local.server") + nameFileFinal
    Video.convertAudioToMp3(voice, ConfigFactory.load().getString("audio.local.server") + voice.nameFile.get, pathFinalLocal)
    val file = new java.io.File(pathFinalLocal)
    val nameFolder = UtilVideo.getFolderToSave(voice.nameFile.get)
    conexionS3.uploadObject(file, "processed/" + nameFolder + "/" + nameFileFinal, s3Client)
    changeStatusVoice(voice)
    println("processed" + voice.voiceId)
    Future {
      sendEmail(voice)
    }
  }

  def downloadVoice(voice: Voice): Unit = {
    var conexionS3 = new ConexionS3();
    var s3Client = conexionS3.createClientS3();
    conexionS3.downloadObject(voice.filevoiceoriginal.get, s3Client, voice.nameFile.get)
  }

  def changeStatusVoice(voice: Voice): Unit = {
    val urlUpdate = ConfigFactory.load().getString("url.update.voice")
    val url = s"${urlUpdate}${voice.voiceId}"
    scala.io.Source.fromURL(url).mkString
  }

  def sendEmail(voice: Voice): Future[Unit] = Future {
    val email = voice.email.get
    val urlSendEmail = ConfigFactory.load().getString("url.send.email")
    val url = s"${urlSendEmail}${email}"
    println("sendEmail" + url)
    scala.io.Source.fromURL(url).mkString
  }

  def get(url: String) = scala.io.Source.fromURL(url).mkString
}