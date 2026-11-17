# 866129000 Stump Town (Beast Tamer tutorial): Like, Grrr and Stuff (59015) needs 2 Smelly Kobolds (9390929), which the
# map doesn't spawn by itself.
if sm.hasQuest(59015) and not sm.hasMobsInField(866129000, 9390929):
    pos = chr.getPosition()
    sm.spawnMob(9390929, pos.getX() + 200, pos.getY(), False)
    sm.spawnMob(9390929, pos.getX() + 320, pos.getY(), False)
# Beast Tamer tutorial: the quest also needs 2 Kobold Overseers (9390930) and the map places only 1.
if sm.hasQuest(59015) and sm.getAmountOfMobsInField(866129000, 9390930) < 2:
    pos = chr.getPosition()
    sm.spawnMob(9390930, pos.getX() + 440, pos.getY(), False)
