# Erel Light story fight (693010300), as GMS: one boss and a 300 s clock; beaten, the record's flag and the next scene.
BOSS = 9602673
NEXT_SCENE = 693007138
TIME_LIMIT = 300
sm.setInstanceTime(TIME_LIMIT, 693007022)
sm.spawnMob(BOSS, 300, 1070)
sm.waitForMobDeath(BOSS)
sm.removeClock()
sm.setQRValue(61712, 'dir=1;ren=1', True)
sm.invokeAfterDelay(2000, "warpInstanceOut", NEXT_SCENE, 0)
