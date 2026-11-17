# Erel Light story fight (693010360), as GMS: one boss and a 300 s clock; beaten, the record's flag and the next scene.
BOSS = 9602675
NEXT_SCENE = 693007164
TIME_LIMIT = 300
sm.setInstanceTime(TIME_LIMIT, 693007001)
sm.spawnMob(BOSS, -800, 1050)
sm.waitForMobDeath(BOSS)
sm.removeClock()
sm.setQRValue(61721, 'dir=1;archon1=1;archon2=1', True)
sm.invokeAfterDelay(2000, "warpInstanceOut", NEXT_SCENE, 0)
