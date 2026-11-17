# Portal script GiantBossOut. From the Gollux fight's end (Heart Tree Guardian Grove, 863010710) it leads to
# Memories of the Heart Tree (863010000). From the Grove outside (863000100) it leaves the Phantom Forest for New Leaf
# City, the city the forest belongs to (Memories' own exit leads to that Grove, so the two maps would only lead to
# each other).
if sm.getFieldID() == 863000100:
    sm.warp(600000000, 0)
else:
    sm.warp(863010000, 1)
sm.dispose()
