package controllers

import play.api._
import play.api.mvc._
import actions.TimingOutAction

import helpers.ControllerPayload

import ch.qos.logback.classic.Level

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent._
import scala.concurrent.duration._

object Application extends Controller
    with ControllerPayload {

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET /hbc-microservice-template

    DESCRIPTION
      Check to see if hbc-microservice-template service is running

    RESULT
      Response

    Request: models.ApiRequestModel
      url: String
      server_received_time: String
      api_version: String
      help: String

    ResponseResult: models.ApiResultModel
      results: String

    Error: models.ApiErrorModel
      data: String
      error: String

    Response: models.ApiModel
      request: Request
      response: ResponseResult
      errors: Array Error
  """)
  def index = Action.async { implicit request =>
    Future {
      println("invoke action\n")
      val response = "hbc-microservice-template is up and running!"
      Thread.sleep(20000)
      println("wake up again\n")
      writeResponseGet(response)
    }
    //val timeoutFuture = play.api.libs.concurrent.Promise.timeout(RequestTimeout("Timed Out"), 1.seconds)
    //val other = Future { Thread.sleep(2000); writeResponseGet("hi there") }
    // val goodResult = block(request)
    //Future.firstCompletedOf(Seq(other, goodResult))
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET /hbc-microservice-template/logLevel/{level}

    DESCRIPTION
      Change the log level of this service

    PARAMETERS
      level: Enum(ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF) String <- The log level you want to set

    RESULT
      Response

  """)
  def changeLogLevelGet(levelString: String) = changeLogLevel(levelString)

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    PUT /hbc-microservice-template/logLevel/{level}

    DESCRIPTION
      Change the log level of this service

    PARAMETERS
      level: Enum(ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF) String <- The log level you want to set

    RESULT
      Response

  """)
  def changeLogLevel(levelString: String) = Action { implicit request =>
    Logger.debug("hbc-microservice-template change log level called")
    val level = Level.toLevel(levelString)
    Logger.underlyingLogger.asInstanceOf[ch.qos.logback.classic.Logger].setLevel(level)
    val response = s"Log level changed to $level"
    writeResponseGet(response)
  }
}
