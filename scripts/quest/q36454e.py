# Empress's Gift (36454): handed in to Kinu with the hat on.
KINU = 1541105

if not sm.questEndDialogue(parentID, KINU):
    sm.questStopDialogue(parentID, 1, KINU)
sm.dispose()
