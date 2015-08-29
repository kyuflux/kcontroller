package actors

import akka.actor._
import play.api._
import libs.json._
import controllers.KMessage
import scredis._

object ScreenActor{
def props(out: ActorRef, redis:Redis,channel:String) = Props(new ScreenActor(out, redis,channel))
}

class ScreenActor(out: ActorRef, redis:Redis,channel:String) extends Actor with ActorLogging {
	import redis.dispatcher
	redis.subscriber.subscribe(channel){
	  case message @ PubSubMessage.Message(chnl, messageBytes) => {
	    val messageString = out ! Json.parse(message.readAs[String]())
	}
  }	
	def receive = {
		case msg:JsValue => log.info(msg.toString)
		 
	}
}
