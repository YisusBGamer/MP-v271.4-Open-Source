# Breaking the Boom Barrier (Xenon 23692), Prison 931060053: Roo-D's start dialogue, then the Boom Barrier
# (npc 2159419) becomes the monster to destroy (9300646).
def barrier_to_mob():
    if sm.getFieldID() != 931060053 or sm.hasMobsInField(931060053, 9300646):
        return
    npc = sm.getField().getLifeByTemplateId(2159419)
    pos = npc.getPosition() if npc is not None else chr.getPosition()
    sm.removeNpc(2159419)
    sm.spawnMob(9300646, pos.getX(), pos.getY(), False)

if sm.questStartDialogue(parentID, 2159399):
    barrier_to_mob()
sm.dispose()
