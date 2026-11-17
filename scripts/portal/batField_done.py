# Portal script batField_done.
# Grand Athenaeum: Forest of Peace bat flock: done, back to the Pitch-Black Forest.
DEST = {
    302030400: (302030100, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
