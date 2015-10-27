package com.example

import akka.actor.ActorSystem
import com.example.persistence.CountUpActor
import com.example.persistence.CountUpActor.CountUp

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ApplicationMain extends App {
  val system = ActorSystem("MyActorSystem")

  val actor = system.actorOf(CountUpActor.props, "c1")

  actor ! CountUp(1)
  actor ! CountUp(1)
  actor ! CountUp(1)

  Await.result(system.whenTerminated, Duration.Inf)
}