# Portal script up_end00.
# Grand Athenaeum: Black Wings prison: up to Eleanor's room.
DEST = {
    304020000: (304030000, 0),
    304020010: (304030000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
