package com.marcom.sports.model

import java.util.UUID


case class Player(id:UUID,name:String)

case class Team(id:UUID,name:String,players:List[Player])

case class SportsFixture(id:UUID, desc:String, home:Team,away:Team)
