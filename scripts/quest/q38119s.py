# 38119 [Hotel Arcus] Slow and Steady - start (Belle, Hotel Arcus Saloon). No end NPC: done once Belle has spoken.
BELLE = 3005012

sm.setSpeakerID(BELLE)
sm.setBoxChat()
sm.sendNext("We made it... I really thought that train was going to take us all the way to the end of the world.")
sm.sendNext("No treasure, either. Just a runaway engine and a lot of angry robots.")
if sm.sendAskAccept("Still, you didn't panic once. Slow and steady, huh? I guess that's how you get things done "
                    "around here."):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.completeQuestNoCheck(parentID)
    sm.sendNext("Archelon looks like he wants a word with you. Go on.")
sm.dispose()
