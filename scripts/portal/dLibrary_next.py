# Portal script dLibrary_next.
# Grand Athenaeum: Forest of Beginnings: on to the next part.
DEST = {
    303010100: (303010200, 0),
    303010200: (303010300, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
