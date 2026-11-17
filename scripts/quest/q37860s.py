# 37860 [Esfera] Spider Hunt (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#There's a #bspider swarm#k, just like Melange said. A present from Will.")
sm.sendNext("#face0#It'd be a good idea to deal with them now, before they really start multiplying.")
if sm.sendAskYesNo("#face0#Head to #bMirror-touched Sea 2#k and hunt 200 #bAranya#k."):
    sm.startQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h0;45=h0;46=h0;47=h1;77=h0;78=h0;79=h0;80=h0")
else:
    sm.sendNext("#face0#Not ready yet?")
