# Secret Place: Touch of Oda / Fact or Fiction (57433): 5 Oda Spirit Walkers and a Black Wings Ninja, around the
# entrance (the map has no spawn points of its own).
if sm.hasQuest(57433) and not sm.hasMobsInField():
    pos = chr.getPosition()
    for dx in (-240, -120, 120, 240, 360):
        sm.spawnMob(9421565, pos.getX() + dx, pos.getY(), False)  # Oda Spirit Walker
    sm.spawnMob(9421566, pos.getX() + 200, pos.getY(), False)  # Black Wings Ninja
