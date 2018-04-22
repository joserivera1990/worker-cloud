package aws.s3

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import com.amazonaws.services.s3.model.{GetObjectRequest, PutObjectRequest}
import java.io.File

import com.typesafe.config.ConfigFactory

import scala.reflect.io.Path

class ConexionS3 {

  def createClientS3(): AmazonS3 = {

    val acceskey = ConfigFactory.load().getString("aws.s3.acceskey")
    val secretkey = ConfigFactory.load().getString("aws.s3.secretkey")
    val awsCreds = new BasicAWSCredentials(acceskey, secretkey)
    val s3Client = AmazonS3ClientBuilder.standard()
      .withRegion(Regions.US_EAST_1)
      .build()
    return s3Client
  }

  def uploadObject(file: File, path: String, s3Client: AmazonS3): Boolean = {
    val bucketName = ConfigFactory.load().getString("aws.s3.bucketName")
    val putObject = new PutObjectRequest(bucketName, path, file)
    val putObjectResult = s3Client.putObject(putObject)
    if (putObjectResult.getContentMd5 != "") return true
    return false
  }

  def downloadObject(path: String, s3Client: AmazonS3, nameFile: String): Unit = {

    val newPath = path.replace(ConfigFactory.load().getString("cloudfront.path"), "")
    val newPathCreate: Path = Path(ConfigFactory.load().getString("audio.local.server"))
    newPathCreate.createDirectory(false)
    s3Client.getObject(
      new GetObjectRequest("concurso10", newPath),
      new File(ConfigFactory.load().getString("audio.local.server") + s"${nameFile}"))
  }

}
