package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import kafka.producer.KeyedMessage
import models.{JsonSerializer, Message}
import global.Global
import Global.kafkaProducer

object Application extends Controller {
  val topic = "notifications"

  val messageForm = Form(
    mapping(
      "name" -> text,
      "message" -> text
    )(Message.apply)(Message.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.index(messageForm))
  }

  def sendMessage = Action { implicit request =>
    val message = messageForm.bindFromRequest.get
    kafkaProducer.map(_.send(KeyedMessage(topic, null, JsonSerializer.toJsonString(message))))
    Ok(views.html.index(messageForm)).flashing("info" -> "Message sent!")
  }
}

