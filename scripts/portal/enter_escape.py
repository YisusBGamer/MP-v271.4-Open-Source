# Portal script enter_escape.
# Grand Athenaeum: Eleanor's room: break out.
DEST = {
    304030000: (304030100, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
