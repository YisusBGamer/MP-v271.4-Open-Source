# Oni Forest scene maps (Mushroom Shrine Tales): the way on at the end of the path.
#   Oni Forest Entrance (no Oni left) -> Beginning of the Perilous Path, where Ibaraki waits.
#   Perilous Path (top, past the Sacred Idols) -> End of the Perilous Path.
DEST = {
    800021000: (800021102, 0),
    800021010: (800021104, 0),
}

dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
