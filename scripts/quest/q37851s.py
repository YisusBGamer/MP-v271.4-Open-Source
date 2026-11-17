# 37851 [Esfera] The Strangers (start)
JEAN = 3003406

sm.setSpeakerID(JEAN)
sm.setBoxChat()
sm.sendNext("#face0##h0#! We've got a problem! Where are you? You need to get to Trueffet Square right now.")
sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("What's going on?")
sm.setSpeakerID(JEAN)
sm.setBoxChat()
sm.sendNext("#face0#Some suspicious folks have been seen poking around Morass.")
sm.sendNext("#face0#I think they're looking for signs that the Erda has been disturbed. Disturbed by outsiders like you, I mean.")
sm.sendNext("#face0#They may not be dangerous, but you should talk to them before they cause trouble. I can't leave Trueffet right now, I'm not feeling stable.")
if sm.sendAskYesNo("#face0#At this rate they'll reach Trueffet soon. Please hurry.\r\n#b(If you accept, you will be moved to Abandoned Area 4.)#k"):
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
    sm.createQuestWithQRValue(34560, "40=h1;77=h1;78=h1")
    sm.warp(450006330)
else:
    sm.sendNext("#face0#Talk to me when you're ready.")
