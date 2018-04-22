package aws.sqs

import java.util.{ HashMap, List }

import com.amazonaws.auth.{ AWSStaticCredentialsProvider, BasicAWSCredentials }
import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.{ AmazonSQS, AmazonSQSClientBuilder }
import com.amazonaws.services.sqs.model.{ Message, MessageAttributeValue, ReceiveMessageRequest, SendMessageRequest }
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

class ConexionSQS {
  val queueUrl = ConfigFactory.load().getString("aws.sqs.queueUrl")

  def conexionSQS(): AmazonSQS = {
    val acceskey = ConfigFactory.load().getString("aws.sqs.acceskey")
    val secretkey = ConfigFactory.load().getString("aws.sqs.secretkey")
    val awsCreds = new BasicAWSCredentials(acceskey, secretkey)
    val sqsClient = AmazonSQSClientBuilder.standard
      .withRegion(Regions.US_EAST_1).withCredentials(new AWSStaticCredentialsProvider(awsCreds))
      .build()
    return sqsClient
  }

  def sendMessage(id: String, mensaje: String, sqsClient: AmazonSQS): Boolean = {
    try {
      val messageAttributes = new HashMap[String, MessageAttributeValue];
      messageAttributes.put("value", new MessageAttributeValue().withDataType("String").withStringValue(id));
      val sedMensaje = new SendMessageRequest().withQueueUrl(queueUrl)
        .withMessageBody(mensaje).withMessageAttributes(messageAttributes)
      sqsClient.sendMessage(sedMensaje)
      return true
    } catch {
      case e: Exception => {
        return false
      }
    }
  }

  def receiveMessageAll(sqsClient: AmazonSQS): List[Message] = {
    val messages = sqsClient.receiveMessage(queueUrl).getMessages();
    return messages
  }

  def receiveMessage(sqsClient: AmazonSQS): List[Message] = {
    var receiveMessageRequest = new ReceiveMessageRequest()
      .withQueueUrl(queueUrl).withMaxNumberOfMessages(1) //un solo mensaje
    val messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
    return messages
  }

  def deleteAllMessage(messages: List[Message], sqsClient: AmazonSQS) = {
    for (message <- messages.asScala) {

      sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
    }
  }

}
