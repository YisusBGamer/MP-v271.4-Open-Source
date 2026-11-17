# Erel Light's first fight (693007180), as GMS: the skill guide, three of each shadow and a 150 s clock, the kill
# count; all six beaten, the record's flag and the next scene.
NEXT_SCENE = 693007171
RETURN_MAP = 693007045
TIME_LIMIT = 150
MOBS = [9602671, 9602671, 9602671, 9602672, 9602672, 9602672]
sm.setInstanceTime(TIME_LIMIT, RETURN_MAP)
sm.avatarOrientedRepeat("UI/tutorial.img/Erel/skill", 5000)
for i, mob in enumerate(MOBS):
    sm.spawnMob(mob, -600 + i * 200, 1063)
for killed in range(1, len(MOBS) + 1):
    sm.waitForMobDeath(*set(MOBS))
    sm.chatScript("Enemies Defeated: %d/%d" % (killed, len(MOBS)))
sm.removeClock()
sm.setQRValue(61696, 'dir=1;mob=1', True)
sm.invokeAfterDelay(2000, "warpInstanceOut", NEXT_SCENE, 0)
