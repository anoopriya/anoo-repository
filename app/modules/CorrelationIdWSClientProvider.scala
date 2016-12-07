package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{Configuration, Environment}
import monitoring.CorrelationIdWSClient
import play.api.libs.ws.WSClient

class CorrelationIdWSClientProvider(
    environment:   Environment,
    configuration: Configuration
) extends AbstractModule {
  def configure() = {
    bind(classOf[WSClient]).annotatedWith(Names.named("CorrelationID")).to(classOf[CorrelationIdWSClient])
  }
}

