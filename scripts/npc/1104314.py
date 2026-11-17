# Francis (1104314) in the Puppeteer's Hideout (910510002): Puppeteer Takedown (20913, Cygnus Knights).
if sm.hasQuest(20912) and not sm.hasHadQuest(20913):
    if sm.questStartDialogue(20913, 1104314):
        pos = chr.getPosition()
        sm.removeNpc(1104314)
        sm.spawnMob(9300740, pos.getX() + 150, pos.getY(), False)
else:
    sm.setSpeakerID(1104314)
    sm.sendSayOkay("WAAAAAH!")
sm.dispose()
