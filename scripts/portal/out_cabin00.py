# Cabin door back up to the ship deck (Orbis <-> Victoria ships)
DEST = {
    200090910: (200090900, 4),  # Cabin - Victoria Bound -> Victoria Bound
    200090930: (200090920, 4),  # Cabin - Orbis Bound -> Orbis Bound
}

dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
