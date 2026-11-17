# Portal script go_up00.
# Grand Athenaeum: Black Wings prison: up to the mine elevator.
DEST = {
    304020000: (304020300, 0),
    304020010: (304020300, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
