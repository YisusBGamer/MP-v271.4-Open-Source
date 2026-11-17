# 37854 [Esfera] Back to Base (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#I haven't seen any flares from Shubert or Melange. I guess we should make the first move.")
sm.sendNext("#face0##h0#, while you were looking for the flare, I scouted out a spot to launch it from. I did my part!")
if sm.sendAskYesNo("#face0#If you're done here, shall we go?\r\n\r\n#b(If you accept, you will fire the signal flare.)#k"):
    sm.startQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h1;31=h1;32=h1;40=h0;41=h0;42=h0;77=h0;78=h0")
    sm.warp(450007040)
else:
    sm.sendNext("#face0#The flare's ready. I think we should hurry.")
