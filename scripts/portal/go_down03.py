# Portal script go_down03.
# Grand Athenaeum: Black Wings prison: down to the second damp basement.
DEST = {
    304020000: (304020200, 0),
    304020010: (304020200, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
