# 37856 [Esfera] Collecting Erda (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Let's see... It says we need to #bdraw Erda out of Erda#k and inject it into the distorted Erda. Huh.")
sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("He means we have to inject stabilized Erda into the distorted one.")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#In that case we should fight the monsters nearby and gather some #bErda Energy#k.")
if sm.sendAskYesNo("#face0#Let's get 10 Erda Energy from those #bBellalion#k."):
    sm.startQuest(parentID)
else:
    sm.sendNext("#face0#Huh? Is something wrong?")
