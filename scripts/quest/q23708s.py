# Encounter (Xenon story) (23708): the fight happens in a copy of the Alchemy Lab of your own, where Beryl attacks
# (she is spawned here, the map has no script of its own). Beat her, then talk to her. If you fail, forfeit and
# start the quest again.
if sm.questStartDialogue(parentID):
    sm.warpInstanceIn(926160001, 0)
    if sm.getFieldID() == 926160001 and not sm.hasMobsInField():
        sm.spawnMob(9300638, -250, 27, False)
sm.dispose()
