# 37864 [Esfera] In Search of Tana (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#The scenery changed again... We must be getting close.")
if sm.sendAskYesNo("#face0#Looks like the Executors went on ahead. Let's hurry!"):
    sm.startQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h0;45=h0;46=h0;47=h0;48=h0;49=h0;50=h1;52=h0;53=h1;77=h0;78=h0;79=h0;80=h0")
else:
    sm.sendNext("#face0#What else did you want to do here?")
