# 270020500 (see CustomFieldScripts): [Temple of Time] test (3512) - the Ice Knight (8220005) appears for a character with the
# quest when none is here.
if sm.hasQuest(3512) and not sm.isComplete(3512) and not sm.hasMobsInField(270020500, 8220005):
    pos = chr.getPosition()
    sm.spawnMob(8220005, pos.getX() + 250, pos.getY(), False)
    sm.chat("The Ice Knight appears!")
