package actors

import akka.actor._
import play.api._


object LobbyActor{
def props(out: ActorRef) = Props(new LobbyActor(out))
}

class LobbyActor(out: ActorRef) extends Actor with ActorLogging {
	def receive = {
		case msg:String => { 
			out ! msg
			log.info(msg)
			}
	}
}
