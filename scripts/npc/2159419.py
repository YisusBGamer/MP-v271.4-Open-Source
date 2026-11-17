# Boom Barrier (2159419), Prison 931060053: Breaking the Boom Barrier (Xenon 23692).
if sm.hasQuest(23692) and not sm.hasMobsInField(931060053, 9300646):
    npc = sm.getField().getLifeByTemplateId(2159419)
    pos = npc.getPosition() if npc is not None else chr.getPosition()
    sm.removeNpc(2159419)
    sm.spawnMob(9300646, pos.getX(), pos.getY(), False)
    sm.chat("Use a long range attack to break the Boom Barrier.")
else:
    sm.chat("The barrier will electrocute you on contact.")
sm.dispose()
