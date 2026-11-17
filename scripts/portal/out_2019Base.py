# Maple Alliance Outpost (993120000) east00: back where the player came from, else Limina's town
LIMINA = 450012000
STORY_MAPS = (993120000, 993136000)  # the Outpost and the Convergence story maps

back = chr.getPreviousFieldID()
if back <= 0 or STORY_MAPS[0] <= back < STORY_MAPS[1]:
    back = LIMINA
sm.warp(back, 0)
sm.dispose()
