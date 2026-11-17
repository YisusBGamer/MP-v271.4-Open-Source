# Erel Light story fight (693010320), as GMS: one boss and a 300 s clock; beaten, the record's flag and the next scene.
BOSS = 9602670
NEXT_SCENE = 693007141
TIME_LIMIT = 300
sm.setInstanceTime(TIME_LIMIT, 693007022)
sm.spawnMob(BOSS, 300, 1070)
sm.waitForMobDeath(BOSS)
sm.removeClock()
sm.setQRValue(61713, 'start=1;boss=1', True)
sm.invokeAfterDelay(2000, "warpInstanceOut", NEXT_SCENE, 0)
