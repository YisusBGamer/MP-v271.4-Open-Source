# 37864 [Esfera] In Search of Tana (end)
OLLIE = 3003500
LIGHT_EXECUTOR = 3003504

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Hm? Why did you stop?")
sm.setSpeakerID(LIGHT_EXECUTOR)
sm.setBoxChat()
sm.sendNext("#face0#We cannot go on.")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#After all this, you can't go any further?")
sm.setSpeakerID(LIGHT_EXECUTOR)
sm.setBoxChat()
sm.sendNext("#face0#She has raised a barrier. But there is another way.")
sm.completeQuest(parentID)
