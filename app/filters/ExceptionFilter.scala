
package filters

import javax.inject._

import akka.stream.Materializer
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import scala.concurrent._

class ExceptionFilter @Inject() (val mat: Materializer) extends Filter {
  def apply(next: RequestHeader => Future[Result])(req: RequestHeader): Future[Result] = {

    def doStuffWithError(): PartialFunction[Throwable,Throwable] = {
     case throwable ⇒
       //do some stuff
       Logger.debug(throwable.getStackTrace.mkString("\n"))

       //give the throwable back to the code so it can continue execution
       throwable
   }

    next(req) recover (doStuffWithError andThen helpers.ControllerPayloadLike.defaultExceptionHandler(req))
  }
}

