# Portal script pt_findingArin.
# Aurora High Forest 2 (Grand Athenaeum, Episode I) -> Omen's Call. During Arin About (32644) the Omens attack
# (your own copy).
if sm.hasQuest(32644):
    sm.warpInstanceIn(302040500, 0)
    field = chr.getField()
    if field.getId() == 302040500:
        for i in range(15):
            field.spawnMob(8230047, -1000 + i * 110, 185, False, 0)
else:
    sm.warp(302040500, 0)
sm.dispose()
