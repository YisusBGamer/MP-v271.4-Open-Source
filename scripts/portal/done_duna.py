# Portal script done_duna.
# Grand Athenaeum: Full-force breakthrough: out toward the mine outskirts.
DEST = {
    304030300: (304040000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
