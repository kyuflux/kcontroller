package actors

import akka.actor._
import play.api._
import libs.json._
import controllers.KMessage

object LobbyActor{
def props(out: ActorRef, src:ActorRef, url:String) = Props(new LobbyActor(out, src, url))
}

class LobbyActor(out: ActorRef, src:ActorRef, url:String) extends Actor with ActorLogging {
	def receive = {
		case msg:JsValue => {
			(msg \ "to").asOpt[String].foreach { to =>
				(msg \ "customer").asOpt[JsValue].foreach{
				   customer => src ! KMessage("POST",s"$url/$to",customer)
				} 
			}
		} 
	}
}
