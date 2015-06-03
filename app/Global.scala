import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent.Future

object Global extends GlobalSettings {

  override def doFilter(next: EssentialAction): EssentialAction =
    Filters(super.doFilter(next), GlobalFilter)
}
