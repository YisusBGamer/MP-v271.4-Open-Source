# Portal script next_erebmonpa.
# Grand Athenaeum: Retaking Ereve: on to the next part.
DEST = {
    304060100: (304060200, 0),
    304060200: (304060300, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
