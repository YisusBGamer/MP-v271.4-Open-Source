# Land of the Dead: Grave of the Wandering Dead, west end -> Neglected Cemetery (east end).
DEST = {
    800022120: (800022100, 2),
    800022125: (800022105, 0),
}

dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
