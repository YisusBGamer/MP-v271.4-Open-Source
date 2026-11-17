# 37871 [Esfera] Mirrors in Mirrors (start): into your own copy of the mirror within the mirror, where Will's
# Reflection waits
MELANGE = 3003501

sm.setSpeakerID(MELANGE)
sm.setBoxChat()
sm.sendNext("#face0#I think I finally know everything I need to know.")
sm.sendNext("#face0#At this rate, the power of the #rTranscendents of two worlds#k will become one. Fighting a force that strong would be hopeless.")
sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("...")
sm.setSpeakerID(MELANGE)
sm.setBoxChat()
sm.sendNext("#face0#The door Tana spoke of will take us to another Mirror World inside the Mirror World.")
sm.sendNext("#face0#There's nothing more I can do for you. I can't fight inside his Mirror World.")
sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
if sm.sendAskYesNo("Looks like this is it. I'm going in."):
    sm.setSpeakerID(MELANGE)
    sm.setBoxChat()
    sm.sendNext("#face0#You'll still be able to hear my voice, so I'll advise you as much as I can from here.")
    sm.setBoxChat()
    sm.flipBoxChat()
    sm.flipBoxChatPlayerAsSpeaker()
    sm.sendNext("(Enter the mirror and face Will.)")
    if sm.canHold(1712006):
        sm.startQuest(parentID)
        sm.giveItem(1712006)
        sm.createQuestWithQRValue(34560, "30=h1;31=h1;32=h1;33=h1;40=h0;41=h0;42=h0;44=h0;45=h0;46=h0;47=h0;48=h0;49=h0;50=h0;51=h0;52=h0;53=h0;54=h0;55=h0;56=h0;57=h0;58=h0;77=h0;78=h0;79=h0;80=h0")
        sm.warpInstanceIn(450007510, 0)  # Will's Reflection, field/enter_450007510
    else:
        sm.sendNext("I should make one free slot in my equip inventory before accepting this quest.")
else:
    sm.setSpeakerID(MELANGE)
    sm.setBoxChat()
    sm.sendNext("#face0#(Hmm... Are you a little scared?)")
