# 37870 [Esfera] Moon and Sun (start)
MELANGE = 3003501

sm.setSpeakerID(MELANGE)
sm.setBoxChat()
sm.sendNext("#face0#Hmm... Can you feel it? We're getting closer to the #rGate#k.")
sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("...!")
sm.setSpeakerID(MELANGE)
sm.setBoxChat()
sm.sendNext("#face0#Don't worry. We still have a chance.")
sm.sendNext("#face0#But before that, there's one more thing we need to learn. I just mentioned it. You haven't forgotten, have you?")
sm.sendNext("#face0#The #rBlack Mage's#k goal.")
if sm.sendAskYesNo("#face0#This is probably the last memory. Same as before, deal with the Executors. About 200 #bDark Executors#k should do."):
    sm.startQuest(parentID)
else:
    sm.sendNext("#face0#Can't you see we're in a hurry?")
