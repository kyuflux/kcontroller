package actors

import akka.actor._
import play.api._
import libs.json._
import controllers.KMessage

object CallerActor{
def props(out: ActorRef, src:ActorRef, url:String) = Props(new CallerActor(out, src, url))
}

class CallerActor(out: ActorRef, src:ActorRef, url:String) extends Actor with ActorLogging {
	def receive = {
		case msg:JsValue => {
			(msg \ "queue").asOpt[String].foreach { q =>
				src ! KMessage("GET",s"$url/$q",JsNull)
			}
		} 
	}
}
