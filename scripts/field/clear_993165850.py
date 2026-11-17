# The First Void (993165850) cleared: the statue's memory, then back to the tower (Bottom Floor), which has rebuilt itself.
# Finishes [Karote] The First Statue (38206).
QUEST = 38206
FLOOR = 410005002


def memory():
    sm.setPlayerAsSpeaker()
    sm.sendNext("(The last guardian crumbled, and the statue's light filled the Void. Someone else's memory "
                "flooded your mind...)")
    sm.sendNext("(A white room. Cold tables. A young man with the statue's face, strapped down while voices argue over him.)")
    sm.sendNext("('Unstable. The vessel rejects the power. Discard it.')")
    sm.sendNext("(The floor opened beneath him, and he fell... into the dark sea, far below.)")

if sm.hasQuest(QUEST):
    try:
        memory()
    finally:
        # also when the dialog is closed: the memory was seen
        if sm.hasQuest(QUEST):
            sm.completeQuestNoCheck(QUEST)
        sm.warpInstanceOut(FLOOR, 0)
