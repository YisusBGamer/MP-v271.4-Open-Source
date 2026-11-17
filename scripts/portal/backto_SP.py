# Portal script backto_SP.
# Grand Athenaeum: Danger zone trap: back to the start of the zone.
DEST = {
    304010300: (304010300, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
