# 866133000 Stump Town (Beast Tamer tutorial): Like, Grrr and Stuff (59015) needs 2 Smelly Kobolds (9390929), which the
# map doesn't spawn by itself.
if sm.hasQuest(59015) and not sm.hasMobsInField(866133000, 9390929):
    pos = chr.getPosition()
    sm.spawnMob(9390929, pos.getX() + 200, pos.getY(), False)
    sm.spawnMob(9390929, pos.getX() + 320, pos.getY(), False)
