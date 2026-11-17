# Portal script infiltrarion_out.
# Grand Athenaeum: Gelimer's lab: on to the blocked passage.
DEST = {
    304000100: (304010000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
