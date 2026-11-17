# 910600101 Hidden Path (east of the Golem's Temple Entrance), in00: Puppeteer's Hidden Cave, for Touch of Chaos
# (Hayato 57129), where Akechi Mitsuhide meets the Black Wings.
# Graph.img gives portal 26 the targets 910600102-105 (Puppeteer's Hidden Cave) and 874010028 (Francis's Hideout);
# 910600102 is the cave whose out00 leads back to 910600101's in00.
if sm.hasQuest(57129):
    sm.warpInstanceIn(910600102, 0)
else:
    sm.chat("The cave is empty.")
sm.dispose()
