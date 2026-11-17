# 37856 [Esfera] Collecting Erda (end)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#That's all of it. You're still quick as lightning.")
if sm.sendAskYesNo("#face0#Let's try it before more monsters show up.\r\n\r\n#b(If you accept, you will look into the distorted Erda.)#k"):
    sm.completeQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h1;77=h0;78=h0;79=h0")
else:
    sm.sendNext("#face0#You might want to wait, but the monsters won't.")
