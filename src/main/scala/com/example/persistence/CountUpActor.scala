package com.example.persistence

import akka.actor.{ActorLogging, Props}
import akka.persistence.PersistentActor
import com.example.persistence.CountUpActor.{CountUp, Increased}

class CountUpActor extends PersistentActor with ActorLogging {

  var count: Int = 0

  override def persistenceId: String = self.path.name

  override def receiveCommand: Receive = {
    case c: CountUp =>
      log.info("receive command {}", c)
      persist(Increased(c.count)) {
        event =>
          count += event.diff
          log.info("current count {}", count)
      }
  }

  override def receiveRecover: Receive = {
    case e: Increased =>
      log.info("receive recover {}", e)
      count += e.diff
  }
}

object CountUpActor {
  def props = Props[CountUpActor]

  case class CountUp(count: Int)

  case class Increased(diff: Int)

}