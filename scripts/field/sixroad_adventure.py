# 910400200 Six Path Crossway (story copy): Sugar and the Great Tree (32242) - the Tree Bug (Tree Worm 9300801).
if sm.hasQuest(32242) and not sm.isComplete(32242) and not sm.hasMobsInField(910400200, 9300801):
    pos = chr.getPosition()
    sm.spawnMob(9300801, pos.getX() + 250, pos.getY(), False)
    sm.chat("Get rid of the Tree Bugs for Sugar!")
