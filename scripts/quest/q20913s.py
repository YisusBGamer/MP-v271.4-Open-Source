# Puppeteer Takedown (20913, Cygnus Knights): started at Francis in the Puppeteer's Hideout (910510002); he fights.
if sm.questStartDialogue(parentID, 1104314) and sm.getFieldID() == 910510002 and not sm.hasMobsInField():
    pos = chr.getPosition()
    sm.removeNpc(1104314)
    sm.spawnMob(9300740, pos.getX() + 150, pos.getY(), False)
sm.dispose()
