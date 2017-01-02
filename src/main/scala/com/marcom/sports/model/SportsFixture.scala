package com.marcom.sports.model

case class FixtureId(value:String) extends AnyVal

case class SportsFixture(id:FixtureId,desc:String,result:String)
