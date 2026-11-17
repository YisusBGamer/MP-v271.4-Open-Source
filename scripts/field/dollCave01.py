# 910510201 Puppeteer's Cave: the ambush of Eliminate the Puppeteer! (Aran 21731). (910050300 Abandoned Cave, Evan's
# puppet, uses this script too: nothing to do there.)
if sm.getFieldID() == 910510201 and sm.hasQuest(21731) and not sm.hasMobsInField():
    pos = chr.getPosition()
    sm.spawnMob(9300344, pos.getX() + 300, pos.getY(), False)
