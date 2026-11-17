# 272010200 Dark Temple of Time: Black Mage's Main Corridor. [Arkarium] Root of the Conspiracy (31178): Arkarium's
# clone (8860001) is about to remove the Black Mage's seal.
if sm.hasQuest(31178) and not sm.isComplete(31178) and chr.getInstance() is not None and not sm.hasMobsInField(272010200, 8860001):
    pos = chr.getPosition()
    sm.spawnMob(8860001, pos.getX() + 350, pos.getY(), False)
    sm.chat("Eliminate Arkarium before he removes the Black Mage's seal!")
