package modules

import com.google.inject.name.Names
import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import webservices.toggles.TogglesClient

class TogglesClientProvider(
    environment:   Environment,
    configuration: Configuration
) extends AbstractModule {
  def configure() = {
    val svcUrl = configuration.getString("webservices.toggles.url").get
    bind(classOf[String]).annotatedWith(Names.named("toggleServiceURL")).toInstance(svcUrl)
    bind(classOf[webservices.toggles.TogglesClientLike]).to(classOf[TogglesClient])
  }
}
