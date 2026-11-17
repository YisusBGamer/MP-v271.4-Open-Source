# 910510002 The Puppeteer's Hideout (Cygnus Knights): Francis waits here for Puppeteer Takedown (20913).
FRANCIS_NPC = 1104314
FRANCIS_MOB = 9300740
pos = chr.getPosition()
if sm.hasQuest(20912) and not sm.hasHadQuest(20913):
    sm.spawnNpc(FRANCIS_NPC, pos.getX() + 300, pos.getY())
elif sm.hasQuest(20913) and not sm.hasMobsInField():
    sm.spawnMob(FRANCIS_MOB, pos.getX() + 300, pos.getY(), False)
