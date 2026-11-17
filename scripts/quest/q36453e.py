# Start the Entry Test (36453), handed in at the far end of the Physical Training Yard; then to Knights Test Site 1.
if sm.questEndDialogue(parentID, 1541110):
    sm.warp(993166405, 0)
elif sm.hasQuest(parentID):
    sm.chat("You haven't met all of this quest's requirements yet.")
sm.dispose()
