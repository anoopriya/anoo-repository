package monitoring

import java.util.UUID
import javax.inject.Inject

import monitoring.Constants._
import org.slf4j.MDC
import play.api.libs.ws.{WSClient, WSRequest}

class CorrelationIdWSClient @Inject() (ws: WSClient) extends WSClient {
  override def underlying[T]: T = ws.underlying
  override def url(url: String): WSRequest = ws.url(url) withHeaders getCorrelationID
  override def close(): Unit = ws.close()

  def getCorrelationID: (String, String) = CORRELATION_ID -> {
    Option(MDC.get(CORRELATION_ID)) getOrElse {
      val newCID = UUID.randomUUID().toString
      MDC.put(CORRELATION_ID, newCID)
      newCID
    }
  }
}

