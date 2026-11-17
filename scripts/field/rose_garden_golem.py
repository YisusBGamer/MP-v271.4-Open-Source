# 211080600 Rose Garden: Hidden Garden 3 (see CustomFieldScripts): [Rose Garden] Crush the King Castle Golem (31042).
# Officially the golem appears after enough monsters are hunted; here it appears for a character with the quest when
# none is around.
if sm.hasQuest(31042) and not sm.isComplete(31042) and not sm.hasMobsInField(211080600, 8211004):
    pos = chr.getPosition()
    sm.spawnMob(8211004, pos.getX() + 300, pos.getY(), False)
    sm.chat("The King Castle Golem appears!")
