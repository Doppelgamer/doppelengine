package controllers

import org.specs2.mutable.Specification
import akka.io.BackpressureBuffer.Ack
import org.scalatest.FunSuite
import game.mobile.Player
import game.communications.connection.RetryingActorConnection

class ApplicationSpec extends FunSuite {
  import Application._

  val ack = """ {"type" : "ack" , "data" : 42} """
  val ku = """{"type" : "keyup", "data" : 65.0}"""
  val kd = """{"type" : "keydown", "data" : 65}"""
  val clk = """{"type" : "click", "data" : {"x" : 42, "y" : 5}}"""

  val unrec0 = """ {"type" : "keydown", "data" : "STRING"} """
  val unrec1 = """ {"type" : "keydown", "KEY" : 65} """

  val clk1 = """ {"type" : "click", "data" : {"x" : "STRING"}} """

  test( "getCommand should return Ack( 42 ) when json = {type: 'ack', data: 42}" ) {
    getCommand( ack ) === RetryingActorConnection.Ack( 42 )
  }

  test( "getCommand should return KeyUp( 65 ) when json = {type : 'keyup', data : 65}" ) {
    getCommand( ku ) === Player.KeyUp( 65 )
  }

  test( "getCommand should retun KeyDown( 65 ) when json = {type : 'keydown', data : 65}" ) {
    getCommand( kd ) === Player.KeyDown( 65 )
  }

  test( "getCommand should retun Click( 42, 5 ) when json = {type : 'click', data : {x: 42, y:5}}" ) {
    getCommand( clk ) === Player.Click( 42, 5 )
  }

  test( "getCommand should return Invalid when the input doesn't follow the spec that getCommand expects" ) {
    getCommand( unrec0 ) === Player.Invalid
    getCommand( unrec1 ) === Player.Invalid
  }
}