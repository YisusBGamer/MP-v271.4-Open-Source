# 36328 The Fox-Eared People - end (Rondo, Pantheon). After it the mysterious voice returns (36329).
sm.setSpeakerID(1541074)
sm.sendNext("Thank you for coming with me. I never thought I'd get this close.")
sm.sendSay("But the barrier... Neither of us can go any further. Not yet.")
if sm.completeQuest(parentID):
    sm.sendSayOkay("One day, when you're stronger, maybe you can help me see it through. I'll be waiting.")
    sm.chatScript("The mysterious voice is ringing in your head again. Open your quest list.")
sm.dispose()
