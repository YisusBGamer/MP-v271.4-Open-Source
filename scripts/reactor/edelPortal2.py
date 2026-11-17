# Crime Prevention System 3 (reactor 3100002), the mine's Intruder Search Warrant room: inspecting it with normal
# attacks sets off Emergency Guard Robots for "Crime Prevention System Inspection" (23949: defeat 150).
QUEST = 23949
GUARD_ROBOT = 9300420

reactor.incHitCount()
if reactor.getHitCount() >= 3:
    if sm.hasQuest(QUEST):
        pos = sm.getPosition(objectID)
        for i in range(3):
            sm.spawnMob(GUARD_ROBOT, pos.getX() + (i - 1) * 40, pos.getY(), False)
    reactor.setHitCount(0)
    reactor.setState(0)
    reactor.broadcastChangeState()
sm.dispose()
