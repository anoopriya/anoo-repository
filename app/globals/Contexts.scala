package globals

import scala.concurrent.ExecutionContext
import play.libs.Akka

object Contexts {
  implicit val ctx: ExecutionContext = Akka.system.dispatchers.lookup("hello-world-service-context")
}
