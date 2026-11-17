# The Third Void (993165950) cleared: the statue's memory, then back to the tower (Top Floor), which has rebuilt itself.
# Finishes [Karote] The Third Statue (38211).
QUEST = 38211
FLOOR = 410005004


def memory():
    sm.setPlayerAsSpeaker()
    sm.sendNext("(The last guardian crumbled, and the statue's light filled the Void. Someone else's memory "
                "flooded your mind...)")
    sm.sendNext("(The automatons bowed as you passed. 'Vessel of the Adversary. Welcome home.')")
    sm.sendNext("(The man from the memories stood at the top of a finished tower, looking straight at you.)")
    sm.sendNext("('You are what I was meant to be. And I am what they threw away.')")

if sm.hasQuest(QUEST):
    try:
        memory()
    finally:
        # also when the dialog is closed: the memory was seen
        if sm.hasQuest(QUEST):
            sm.completeQuestNoCheck(QUEST)
        sm.warpInstanceOut(FLOOR, 0)
