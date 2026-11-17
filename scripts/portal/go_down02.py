# Portal script go_down02.
# Grand Athenaeum: Black Wings prison: down to the damp basement.
DEST = {
    304020000: (304020100, 0),
    304020010: (304020100, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
