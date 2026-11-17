# Cave of Silence (914100022): Secret Organization's Fifth Mission (22588, Evan) - the Ice Wall in front of the
# altar. Break it.
if sm.hasQuest(22588) and sm.getQRValue(22605) != "1" and not sm.hasMobsInField():
    sm.spawnMob(9300391, 300, 145, False)
