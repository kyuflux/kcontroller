package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee._

class Application extends Controller {

	def index = Action {
		Ok(views.html.index("Kcontroller"))
	}
	def kyulobby(org:String, branch:String, lobbyId:String) = Action {
		Ok(views.html.index("kyulobby"))
	}

	def kyucaller(org:String, branch:String, callerId:String) = Action {
		Ok(views.html.index("kyucaller"))
	}
	def kyuscreen(org:String, branch:String, callerId:String) = Action {
		Ok(views.html.index("kyuscreen"))
	}
}
