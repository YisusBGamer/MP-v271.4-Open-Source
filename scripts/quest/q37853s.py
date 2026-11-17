# 37853 [Esfera] In Search of the Signal Flare (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#First things first, we need to find a flare.")
sm.sendNext("#face0#Oh boy. That's a lot of turtles. We'll have to search them one by one, won't we?")
if sm.sendAskYesNo("#face0#Could you hunt the #bAhtuin#k and bring back a #bsignal flare#k? I'll keep an eye out from the ship."):
    sm.startQuest(parentID)
    sm.createQuestWithQRValue(34560, "40=h0;41=h0;42=h1;77=h0;78=h0")
else:
    sm.sendNext("#face0#What are you waiting for?")
