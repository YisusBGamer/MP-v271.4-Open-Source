# 270030500 (see CustomFieldScripts): [Temple of Time] test (3519) - the Temple Guardian (8220006) appears for a character with the
# quest when none is here.
if sm.hasQuest(3519) and not sm.isComplete(3519) and not sm.hasMobsInField(270030500, 8220006):
    pos = chr.getPosition()
    sm.spawnMob(8220006, pos.getX() + 250, pos.getY(), False)
    sm.chat("The Temple Guardian appears!")
