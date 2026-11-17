# The Second Void (993165900) cleared: the statue's memory, then back to the tower (Halfway Up), which has rebuilt itself.
# Finishes [Karote] The Second Statue (38209).
QUEST = 38209
FLOOR = 410005003


def memory():
    sm.setPlayerAsSpeaker()
    sm.sendNext("(The last guardian crumbled, and the statue's light filled the Void. Someone else's memory "
                "flooded your mind...)")
    sm.sendNext("(Experiment after experiment. Pain, then silence, then more pain.)")
    sm.sendNext("(At the bottom of the sea he did not die. He gathered stone and steel, and built, and climbed, and built.)")
    sm.sendNext("('If they will not make me the Adversary, I will make myself one.')")

if sm.hasQuest(QUEST):
    try:
        memory()
    finally:
        # also when the dialog is closed: the memory was seen
        if sm.hasQuest(QUEST):
            sm.completeQuestNoCheck(QUEST)
        sm.warpInstanceOut(FLOOR, 0)
