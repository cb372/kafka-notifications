package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.{JsObject, JsValue}
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import global.Global
import actors.WebSocketBroadcaster.AddChannel
import play.api.Play.current

object Application extends Controller {

  def index = Action {
    val port = Play.configuration.getInt("http.port").getOrElse(9000)
    Ok(views.html.index(port))
  }

  def websocket = WebSocket.using[String] { request =>
    println("Received websocket connect")
    val in = Iteratee.consume[String]()
    val (out, channel) = Concurrent.broadcast[String]
    Global.wsBroadcaster foreach (_ ! AddChannel(channel))

    (in, out)
  }

}