import scala.concurrent.Future
import play.api.mvc._
import play.api.GlobalSettings
import helpers.ControllerPayloadLike

object Global extends GlobalSettings {

  override def doFilter(next: EssentialAction): EssentialAction =
    Filters(
      super.doFilter(next),
      ServiceFilters.TimingFilter,
      ServiceFilters.IncrementFilter,
      ServiceFilters.TimeoutFilter,
      ServiceFilters.ExceptionFilter
    )

  override def onError(request: RequestHeader, ex: Throwable): Future[Result] =
    Future.successful(ControllerPayloadLike.writeResponseFailure(ex.getCause)(request))

}
