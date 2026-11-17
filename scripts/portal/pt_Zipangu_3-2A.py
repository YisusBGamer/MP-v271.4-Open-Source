# Land of the Dead: Neglected Cemetery, east end -> Grave of the Wandering Dead (west end).
# 800022105 / 800022125 are the second copies of the same pair.
DEST = {
    800022100: (800022120, 0),
    800022105: (800022125, 2),
}

dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
