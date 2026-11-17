# Portal script batField_next.
# Grand Athenaeum: Forest of Peace bat flock: on to the next field.
DEST = {
    302030200: (302030300, 0),
    302030300: (302030400, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
