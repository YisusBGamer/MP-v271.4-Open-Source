# 37857 [Esfera] Traces of Tana (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Melange found some more distorted Erda.")
sm.sendNext("#face0#If it's another of Tana's memories, it might lead us to her.")
if sm.sendAskYesNo("#face0#I love it when the pieces fall into place! Let me lead the way!"):
    sm.startQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h0;45=h1;77=h0;78=h0;79=h0;80=h1")
else:
    sm.sendNext("#face0#Huh? I don't think there's anything left to do here.")
