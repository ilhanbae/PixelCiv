package MMO.Server

import java.net.InetSocketAddress

import play.api.libs.json
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json}

case object test

class TCPserver extends Actor {
  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8000))

  var clients: Set[ActorRef] = Set()
  var buffer: String = ""
  val delimiter: String = "~"

  override def receive: Receive = {
    case b: Bound =>
      println("Listening on port: " + b.localAddress.getPort)
    case c: Connected =>
      println("new connection: " + c.remoteAddress)
      this.clients = this.clients + sender()
      sender() ! Register(self)
    case PeerClosed =>
      println("disconnected: " + sender())
      this.clients = this.clients - sender()
    case r: Received =>
      println(r.data.utf8String)
    case test =>

  }

}

object TCPSocketServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher

    import scala.concurrent.duration._

    val server = actorSystem.actorOf(Props(classOf[TCPserver]))

    actorSystem.scheduler.schedule(0 milliseconds, 100 milliseconds, server, test)
  }

}