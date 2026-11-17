# 37863 [Esfera] Will of the World (end)
LIGHT_EXECUTOR = 3003504
OLLIE = 3003500

sm.setSpeakerID(LIGHT_EXECUTOR)
sm.setBoxChat()
sm.sendNext("#face0#They are here.")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Are they... priests like you?")
sm.setSpeakerID(LIGHT_EXECUTOR)
sm.setBoxChat()
sm.sendNext("#face0#They are gatekeepers. Fragments of power.")
sm.sendNext("#face0#They fight the spiders. What matters now is the #bErda#k.")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Erda...? Oh, there's a bookshelf over there, on the ground. Just like when the Erda took the shape of a tombstone before.")
sm.sendNext("#face0#Come to think of it, has the scenery changed? Does that mean we're getting closer?")
sm.setSpeakerID(LIGHT_EXECUTOR)
sm.setBoxChat()
sm.sendNext("#face0#There is no time. Hurry.")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
if sm.sendAskYesNo("#face0#I think we should look into the Erda. Ready?\r\n\r\n#b(If you accept, you will look into the Erda.)#k"):
    sm.completeQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h0;45=h0;46=h0;47=h0;48=h0;49=h1;52=h0;77=h0;78=h0;79=h0;80=h0")
else:
    sm.sendNext("#face0#Don't worry about the spiders. The keepers will drive them off.")
