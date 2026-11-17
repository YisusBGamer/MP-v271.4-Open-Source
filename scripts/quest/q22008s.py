# 22008 |   Chasing away the foxes (Evan intro)
sm.setSpeakerID(1013101)
if sm.sendAskYesNo("How odd. Have you noticed that the chickens aren't laying as many #t4032451#s as they used to? I wonder if the recent increase of foxes has scared the chickens. If that's the case, we really need to take care of this quickly. Don't you agree?"):
    sm.startQuest(parentID)
    sm.sendNext("Right? Let us go and defeat those Foxes. Go on ahead and defeat #r10 Treacherous Foxes#k in #bBack Yard#k first. I'll follow you and take care of what's left behind. Now, hurry over to the Back Yard!")
    sm.dispose()
else:
    sm.dispose()
