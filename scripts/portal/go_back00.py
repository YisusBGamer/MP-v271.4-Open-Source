# Portal script go_back00.
# Grand Athenaeum: Danger zone: back to the blocked passage.
DEST = {
    304010300: (304010000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
