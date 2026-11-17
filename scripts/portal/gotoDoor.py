# 272010100 Temple of Time Corridor 2 -> Black Mage's Main Corridor. [Arkarium] Root of the Conspiracy (31178): a copy
# of the corridor of its own, where Arkarium's clone waits (field/summonAkayrum_1).
if sm.hasQuest(31178) and not sm.isComplete(31178):
    sm.warpInstanceIn(272010200, 1)
else:
    sm.warp(272010200, 1)
sm.dispose()
