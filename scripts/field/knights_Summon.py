#Easy Cygnus Ban Map | Scrubby Garden
KNIGHT_A = 8610028
KNIGHT_B = 8610029
KNIGHT_C = 8610030
KNIGHT_D = 8610031
KNIGHT_E = 8610032

for i in range(3):
    sm.spawnMob(KNIGHT_A, -963, 113)
    sm.spawnMob(KNIGHT_B, -843, 113)
    sm.spawnMob(KNIGHT_C, -723, 113)
    sm.spawnMob(KNIGHT_D, -603, 113)
    sm.spawnMob(KNIGHT_E, -483, 113)

# [Knight Stronghold] Versus Chief Knights (31148, Normal): the five Chief Knights stand guard here too
if sm.hasQuest(31148) and not sm.isComplete(31148):
    x = -1100
    for knight in (8850000, 8850001, 8850002, 8850003, 8850004):  # Mihile, Oz, Irena, Eckhart, Hawkeye
        if chr.getField().getLifeByTemplateId(knight) is None:
            sm.spawnMob(knight, x, 113, False)
        x += 200
