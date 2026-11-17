# Portal script icicle_end.
# Grand Athenaeum: Mysterious Herb Canyon: back to Cygnus's Rest.
DEST = {
    303040000: (303000000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
