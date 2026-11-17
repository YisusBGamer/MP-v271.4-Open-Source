# Portal script go_up01.
# Grand Athenaeum: To Mine Outskirts: out of the mines.
DEST = {
    304040000: (304050000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
