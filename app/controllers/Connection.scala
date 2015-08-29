package controllers

import play.api._
import play.api.mvc._
import actors._
import play.api.Play.current
import libs.json._
import libs.concurrent.Akka
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Broadcast,Source, Flow, Sink, FlowGraph}
import akka.stream.OverflowStrategy.dropHead
import play.api.Play.current
import scredis._
import scala.concurrent.ExecutionContext.Implicits.global
import libs.ws.WS

class Connection extends Controller {
	implicit val system = Akka.system
	implicit val materializer = ActorMaterializer()
	val urlredis = current.configuration.getString("redis.url").getOrElse("none")
	val klogic = current.configuration.getString("klogic.url").getOrElse("none")
	val redis = Redis(host=urlredis)
	val actorSrc = Source.actorRef[KMessage](999,dropHead)
	val end = Sink.foreachParallel[KMessage](10){ k => k.method
		 match {
			case "POST" => WS.url(k.url).post(k.data)
			case "GET" => WS.url(k.url).get()
			} 	
	}
	
	val ref = Flow[KMessage].to(end).runWith(actorSrc)
	
	
	def kyuscreen(org:String, branch:String, screenId:String) = 
		WebSocket.acceptWithActor[JsValue, JsValue] { request => out =>
		val channel = s"$org:$branch"
		ScreenActor.props(out,redis,channel)
	}

	def kyulobby(org:String, branch:String, lobbyId:String) = 
		WebSocket.acceptWithActor[JsValue, JsValue] { request => out =>
		val partialUrl = s"$klogic/$org/$branch"
		LobbyActor.props(out,ref,partialUrl)
	}
	
	def kyucaller(org:String, branch:String, lobbyId:String) = 
		WebSocket.acceptWithActor[JsValue, JsValue] { request => out =>
	  	val partialUrl = s"$klogic/$org/$branch"
	  	CallerActor.props(out,ref,partialUrl)
	}
	
	def kyulobbyWS(org:String, branch:String, lobbyId:String) = Action {
		Results.Accepted
	}
	def kyucallerWS(org:String, branch:String, callerId:String) = Action {
		Results.Accepted
	}
	def kyuscreenWS(org:String, branch:String) = Action { implicit request =>
		request.body.asJson match{
			case Some(obj) => {
				redis.publish[String](s"$org:$branch",Json.stringify(obj))
				}
			case None => ()
		}	
		Results.Accepted
	}

}

case class KMessage(method:String, url:String,data:JsValue)

