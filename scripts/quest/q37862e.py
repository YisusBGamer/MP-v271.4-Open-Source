# 37862 [Esfera] Light and Darkness (end)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Nice work!")
sm.sendNext("#face0#While you were fighting, I scouted the area but found no sign of Will or Tana.")
sm.sendNext("#face0#I guess this tombstone is our only clue.")
if sm.sendAskYesNo("#face0#All right, let's take a look!\r\n\r\n#b(If you accept, you will look into the tombstone.)#k"):
    sm.completeQuest(parentID)
else:
    sm.sendNext("#face0#Are the spiders still bothering you? I think you've beaten enough of them.")
