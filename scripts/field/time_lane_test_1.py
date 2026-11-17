# 270010500 (see CustomFieldScripts): [Temple of Time] test (3505) - the Whale of Time (8220004) appears for a character with the
# quest when none is here.
if sm.hasQuest(3505) and not sm.isComplete(3505) and not sm.hasMobsInField(270010500, 8220004):
    pos = chr.getPosition()
    sm.spawnMob(8220004, pos.getX() + 250, pos.getY(), False)
    sm.chat("The Whale of Time appears!")
