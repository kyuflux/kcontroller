package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

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

	def kyulobbyWS(org:String, branch:String, lobbyId:String) = Action {
		Results.Accepted
	}
	def kyucallerWS(org:String, branch:String, callerId:String) = Action {
		Results.Accepted
	}
	def kyuscreenWS(org:String, branch:String) = Action { implicit request =>
		request.body.asJson match{
			case Some(obj) => println(obj)
			case None => ()
		}	
		Results.Accepted
	}
}
