# 37869 [Esfera] Into the Mirror (start)
MELANGE = 3003501

sm.setSpeakerID(MELANGE)
sm.setBoxChat()
sm.sendNext("#face0#While you were cleaning up all that mess, I analyzed the Mirror World's power, wondering whether it had spread to Esfera.")
sm.sendNext("#face0#Before, you saw memories that had leaked out. This time we'll break into them from this side.")
sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Can we really do that?")
sm.setSpeakerID(MELANGE)
sm.setBoxChat()
sm.sendNext("#face0#*Yawn* That's exactly why I came.")
sm.sendNext("#face0#Tana's memories were swallowed by the Mirror World. We're here to get inside them. Well... there's one problem.")
sm.sendNext("#face0#I can't concentrate with so many of them jumping around.")
if sm.sendAskYesNo("#face0#Defeat 200 #bLight Executors#k and I'll be fine."):
    sm.startQuest(parentID)
    sm.sendNext("#face0#Be careful. To them, we're nothing but intruders right now.")
else:
    sm.sendNext("#face0#I really can't concentrate like this.")
