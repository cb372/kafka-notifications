package global

import play.api.{Application, GlobalSettings}
import kafka.consumer.{ConsumerConnector, ConsumerConfig}
import java.util.Properties
import kafka.serializer.StringDecoder
import play.api.libs.concurrent.Akka
import akka.actor.{ActorRef, Actor, Props}
import actors.WebSocketBroadcaster
import actors.WebSocketBroadcaster.BroadcastMessage

/**
 * Author: chris
 * Created: 1/11/14
 */
object Global extends GlobalSettings {
  val topic = "notifications"

  var kafkaConsumer: Option[ConsumerConnector] = None
  var wsBroadcaster: Option[ActorRef] = None

  def runnable(f: => Unit) = new Runnable() { override def run() { f } }

  override def onStart(app: Application): Unit = {
    implicit val theApp = app
    val actor = Akka.system.actorOf(Props[WebSocketBroadcaster])
    wsBroadcaster = Some(actor)

    val topicCountMap = Map(topic -> 1)
    val consumer = createKafkaConsumer
    kafkaConsumer = Some(consumer)
    val stream = consumer.createMessageStreams(topicCountMap, new StringDecoder, new StringDecoder)(topic).head
    val iterator = stream.iterator()

    new Thread(runnable(
      while (iterator.hasNext()) {
        val msg = iterator.next().message
        //println(msg)
        actor ! BroadcastMessage(msg)
      }
    )).start()
  }

  override def onStop(app: Application): Unit = {
    kafkaConsumer foreach (_.shutdown())
  }

  private def createKafkaConsumer: ConsumerConnector = {
    val props = new Properties()
    props.put("zookeeper.connect", "localhost:2181")
    props.put("group.id", "mygroup")
    props.put("zookeeper.session.timeout.ms", "1000")
    props.put("zookeeper.sync.time.ms", "200")
    props.put("auto.commit.interval.ms", "1000")
    val config = new ConsumerConfig(props)

    kafka.consumer.Consumer.create(config)
  }


}
