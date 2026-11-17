# 37858 [Esfera] Collecting Erda 2 (end)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Happy to report the area is clear. But I keep thinking Will is going to jump out of the shadows...")
if sm.sendAskYesNo("#face0#So what are we waiting for?\r\n#b(If you accept, you will look into the distorted Erda.)#k"):
    sm.completeQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h0;45=h1;77=h0;78=h0;79=h0;80=h0")
else:
    sm.sendNext("#face0#Not ready yet?")
