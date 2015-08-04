package helpers

import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration._
import scala.language.postfixOps
import constants.Constants
import play.libs.Akka
import akka.pattern._
import play.api.mvc._
import globals.Contexts

class TimingoutAction(ea: EssentialAction, timeout: Long = 1000L) extends Action[AnyContent] {
  override def parser = BodyParsers.parse.anyContent

  override def apply(req: Request[AnyContent]): Future[Result] = {
    val goodResult: Future[Result] = ea.apply(req).run
    ControllerTimeoutLike.withTimeout(timeout)(goodResult)
  }
}

trait ControllerTimeout extends ConfigHelper {
  val actionTimeout = config getInt "controllers.timeout"

  // call this with some arbitrary blocking code 
  def timeout[T](time: Long = actionTimeout)(body: => T): Future[T] =
    timingoutFuture(time, Future(body))

  // call this if you already have a future
  def withTimeout[T](time: Long = actionTimeout)(f: Future[T]): Future[T] =
    timingoutFuture(time, f)

  private def timingoutFuture[T](time: Long, f: Future[T]): Future[T] = {
    val timeoutFuture = after(time millis, using = Akka.system.scheduler)(Future.failed(new TimeoutException(Constants.TIMEOUT_MSG)))
    Future.firstCompletedOf(Seq(f, timeoutFuture))(Contexts.ctx)
  }
}

object ControllerTimeoutLike extends ControllerTimeout
