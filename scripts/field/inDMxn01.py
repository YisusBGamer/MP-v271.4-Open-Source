# 910600302 Puppeteer's Hidden Cave - The Secrets of the Puppeteer (23677, Xenon): Francis (Puppeteer 9300641) and
# Dargoth (9300642, Dargoth's look) in your copy of the cave. Beating both runs field/clear_910600302.py.
FRANCIS = 9300641
DARGOTH = 9300642

if sm.hasQuest(23677) and sm.getQRValue(23720) != "1" and not sm.hasMobsInField():
    sm.spawnMob(FRANCIS, 100, 260, False)
    sm.spawnMob(DARGOTH, 400, 260, False)
    sm.chatScript("Defeat Francis and Dargoth of the Black Wings.")
sm.dispose()
