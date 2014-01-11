package global


import java.util.Properties
import kafka.producer.{Producer, ProducerConfig}
import models.Message
import play.api._

object Global extends GlobalSettings {
  var kafkaProducer: Option[Producer[String, String]] = None

  override def onStart(app: Application): Unit = {
    val props = new Properties()
    props.put("metadata.broker.list", "localhost:9092")
    props.put("serializer.class", "kafka.serializer.StringEncoder")

    val config = new ProducerConfig(props)
    val producer = new Producer[String, String](config)
    kafkaProducer = Some(producer)
  }

  override def onStop(app: Application): Unit = {
    kafkaProducer foreach (_.close())
  }

}
