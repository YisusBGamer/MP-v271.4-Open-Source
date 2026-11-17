# 38206 [Karote] The First Statue - start (Irena, Bottom Floor). Touching the statue pulls you into The First Void
# (993165850); clearing it plays the statue's memory and finishes the quest (field/clear_993165850). No end NPC.
IRENA = 3005238
VOID = 993165850
FLOOR = 410005002

sm.setSpeakerID(IRENA)
sm.setBoxChat()
sm.sendNext("#h0#, look at this. A #bstatue#k, right in the middle of the rubble... and not a scratch on it.")
sm.sendNext("Everything else in the tower was crushed. Why would this one thing survive?")
if sm.sendAskAccept("Be careful. Something about it feels... alive. Will you take a closer look?"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(As your hand touched the statue, a strange force dragged you in!)")
        sm.warpInstanceIn(VOID, 0)
        sm.setInstanceTime(15 * 60, FLOOR)
sm.dispose()
