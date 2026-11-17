# 38211 [Karote] The Third Statue - start (Belle, Top Floor). The last statue pulls you into The Third Void
# (993165950); clearing it plays the memory and finishes the quest (field/clear_993165950). No end NPC.
BELLE = 3005246
VOID = 993165950
FLOOR = 410005004

sm.setSpeakerID(BELLE)
sm.setBoxChat()
sm.sendNext("This is the top of the tower. The last statue's right here.")
if sm.sendAskAccept("Whatever's waiting in there is going to be the toughest yet. Ready?"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(The statue's light swallowed you whole!)")
        sm.warpInstanceIn(VOID, 0)
        sm.setInstanceTime(15 * 60, FLOOR)
sm.dispose()
