# Pirate versus Rabbit (32254): Tess confronts the masked rabbit (Suspicious Rabbit 9300802) on the Way to the Beach.
# Started from Tess at the Six Path Crossway instead, it takes you to the beach (explorer_way_to_beach spawns it).
if sm.questStartDialogue(parentID, 1520003):
    if sm.getFieldID() != 912090000:
        sm.warpInstanceIn(912090000, 0)
    elif not sm.hasMobsInField(912090000, 9300802):
        pos = chr.getPosition()
        sm.spawnMob(9300802, pos.getX() + 250, pos.getY(), False)
sm.dispose()
