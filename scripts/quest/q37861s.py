# 37861 [Esfera] Spider's Enemy (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Looks like... a spider. I think we should check it out.")
sm.sendNext("#face0#It seems to be attacking someone.")
sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("I don't see anything...")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#I wasn't made the Cygnus Knights' scout for nothing. You can trust my eyes.")
if sm.sendAskYesNo("#face0#Shall we go?\r\n\r\n#b(If you accept, you will be moved to Mirror-touched Sea 4.)#k"):
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h0;45=h0;46=h0;47=h0;48=h1;77=h0;78=h0;79=h0;80=h0")
    sm.warp(450007130)
else:
    sm.sendNext("#face0#Got something better to do here?")
