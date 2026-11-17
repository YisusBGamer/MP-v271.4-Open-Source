# 38209 [Karote] The Second Statue - start (Belle, Halfway Up). The statue pulls you into The Second Void
# (993165900); clearing it plays the memory and finishes the quest (field/clear_993165900). No end NPC.
BELLE = 3005245
VOID = 993165900
FLOOR = 410005003

sm.setSpeakerID(BELLE)
sm.setBoxChat()
sm.sendNext("That statue's the same as the one downstairs. You're really going to touch it again?")
if sm.sendAskAccept("Fine, but if it eats you this time, I'm not coming in after you! ...Probably."):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(The statue's power pulled you in once more!)")
        sm.warpInstanceIn(VOID, 0)
        sm.setInstanceTime(15 * 60, FLOOR)
sm.dispose()
