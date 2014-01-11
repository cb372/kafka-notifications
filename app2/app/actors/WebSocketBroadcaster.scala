package actors

import akka.actor.Actor
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.json.{JsString, JsObject, JsValue}
import actors.WebSocketBroadcaster.{BroadcastMessage, AddChannel}

/**
 * Author: chris
 * Created: 1/11/14
 */
class WebSocketBroadcaster extends Actor {
  var channels = List[Channel[String]]()

  def receive: Actor.Receive = {
    case AddChannel(channel) => channels = channel :: channels
    case BroadcastMessage(json) => for (c <- channels) {
      println("Pushing json to channel" + json)
      c.push(json)
    }
  }
}

object WebSocketBroadcaster {
  case class BroadcastMessage(json: String)
  case class AddChannel(channel: Channel[String])
}