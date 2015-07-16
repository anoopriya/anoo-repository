import sbt._

/* List the dependencies that are common across all microservices
 * DO NOT list dependencies that are specific to a microservice. Use 'ServiceDependencies' instead. */
object CommonDependencies {

  val scalaTestVersion = "2.2.5"
  val scalaCheckVersion = "1.12.2"
  val apiDocVersion = "14"
  val playWSVersion = "2.3.9"
  val playMockWSVersion = "2.3.0"
  val hystrixVersion = "1.4.12"
  val rxVersion = "0.20.7"

  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  val apiDoc = "com.hbc" %% "api_doc" % apiDocVersion
  val scalacheck = "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
  val playWS = "com.typesafe.play" %% "play-ws" % playWSVersion
  val playMockWS = "de.leanovate.play-mockws" %% "play-mockws" % playMockWSVersion % "test"
  val hystrix    = "com.netflix.hystrix" % "hystrix-core" % hystrixVersion
  val hystrixMetrics = "com.netflix.hystrix" % "hystrix-metrics-event-stream"  % hystrixVersion
  val rx         = "com.netflix.rxjava" % "rxjava-scala" % rxVersion

  val commonDependencies : Seq[ModuleID] =
    Seq(
      playWS,
      playMockWS,
      scalaTest,
      apiDoc,
      scalacheck,
      hystrix,
      hystrixMetrics,
      rx
      )
}
