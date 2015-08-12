package controllers

import play.api._
import play.api.mvc._
import actors._
import play.api.Play.current

class Connection extends Controller {
	def kyulobby(org:String, branch:String, lobbyId:String) = 
		WebSocket.acceptWithActor[String, String] { request => out =>
	  	LobbyActor.props(out)
	}
}


