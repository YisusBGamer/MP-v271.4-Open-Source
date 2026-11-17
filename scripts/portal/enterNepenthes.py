# Portal script enterNepenthes.
# Orbis Park: the Nependeath stem. During To the Sealed Garden (21739, Aran) it leads to the stem that reaches the
# Sealed Garden (where the Giant appears, see field/sealGarden.py); otherwise to the Neglected Strolling Path.
if sm.hasQuest(21739):
    sm.warp(920030000, 2)
else:
    sm.warp(200060001, 2)
sm.dispose()
