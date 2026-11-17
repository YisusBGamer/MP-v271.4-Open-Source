# 37858 [Esfera] Collecting Erda 2 (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#I'll leave that part to you! I'll scout the area in the meantime.")
if sm.sendAskYesNo("#face0#Same as before, we'll probably need about #b10 Erda Energy#k."):
    sm.startQuest(parentID)
else:
    sm.sendNext("#face0#Huh? Did I make a mistake...?")
