# Ship deck door to the cabin (Orbis <-> Victoria ships)
DEST = {
    200090900: (200090910, 4),  # Victoria Bound -> Cabin - Victoria Bound
    200090920: (200090930, 4),  # Orbis Bound -> Cabin - Orbis Bound
}

dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
