# 37862 [Esfera] Light and Darkness (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#The Erda here isn't too unstable, but...")
sm.sendNext("#face0#There are still a lot of Will's spiders around. We should thin them out.")
if sm.sendAskYesNo("#face0#Hunt 200 #bAraneas#k. I'm counting on you!"):
    sm.startQuest(parentID)
else:
    sm.sendNext("#face0#Not ready yet?")
