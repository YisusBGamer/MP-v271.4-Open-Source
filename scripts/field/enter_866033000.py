# 866033000 King Kobold's Lair: King Kobold Knockout (Beast Tamer 59052).
if sm.hasQuest(59052) and not sm.hasMobsInField(866033000, 9390915):
    pos = chr.getPosition()
    sm.spawnMob(9390915, pos.getX() + 300, pos.getY(), False)
