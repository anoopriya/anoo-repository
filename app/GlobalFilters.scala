import play.api.Play.current
//import akka.actor.ActorSystem
import play.api.libs.concurrent.Akka
import play.api.mvc._
import scala.concurrent.Future
import play.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import metrics.StatsDClient

// common logging and metrics for all requests
object GlobalFilter extends Filter with StatsDClient {

	override val system = Akka.system // use play's akka system 

	def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
		val startTime = System.currentTimeMillis
		
		val routeTag = requestHeader.tags.getOrElse(play.api.Routes.ROUTE_ACTION_METHOD, "route tag")
		increment(routeTag)
		nextFilter(requestHeader).map { result =>
			val tagInfo = requestHeader.tags.foldLeft(""){ (acc, kv) => acc + "\n" + kv._1 + " : " + kv._2}
			val endTime = System.currentTimeMillis
			val respTime = (endTime - startTime).toInt
			val tag = requestHeader.tags.getOrElse("trackingtag", "carp not tag")
			timing(routeTag, respTime)
			Logger.debug(s"req $routeTag took $respTime. $tagInfo")
			result
		}
	}
}

