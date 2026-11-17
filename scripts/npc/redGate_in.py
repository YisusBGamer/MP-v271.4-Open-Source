# Mystic Gate (9070010) | Orbis Tower <16th Floor> (200080600) and the Strange Area behind it (920020100)
# "Explosive Reasoning" (1532: "Check out the Mystic Gate in the lower part of Orbis Tower <16th Floor>", defeat 100
# Enhanced Fire Sentinels): the Sentinels only live in the Strange Area (920020100, which returns players to the 16th
# floor). The gate takes players on that quest in, and back out.
ORBIS_TOWER_16F = 200080600
GATE_SPOT = 2  # mysticGate00
STRANGE_AREA = 920020100
EXPLOSIVE_REASONING = 1532
if sm.getFieldID() == STRANGE_AREA:
    sm.warp(ORBIS_TOWER_16F, GATE_SPOT)
elif sm.hasQuest(EXPLOSIVE_REASONING):
    sm.warp(STRANGE_AREA, 0)
sm.dispose()
