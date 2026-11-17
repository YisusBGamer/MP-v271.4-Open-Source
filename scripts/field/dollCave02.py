# 910510202 Puppeteer's Cave: Traces of the Puppeteer (Aran 21734).
if sm.hasQuest(21734) and not sm.hasMobsInField():
    pos = chr.getPosition()
    sm.spawnMob(9300346, pos.getX() + 300, pos.getY(), False)
