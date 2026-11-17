# 37863 [Esfera] Will of the World (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#Tana was a Transcendent in Waiting...? I guess that's why she has so much power.")
sm.sendNext("#face0#But that seems like it happened a long time ago. What's become of her now...?")
sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Hmm...")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#...I have so many more questions! But if we follow them, maybe we'll find the answers.")
if sm.sendAskYesNo("#face0#I think we should follow the Executor."):
    sm.startQuest(parentID)
    sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h0;45=h0;46=h0;47=h0;48=h0;49=h1;52=h1;77=h0;78=h0;79=h0;80=h0")
