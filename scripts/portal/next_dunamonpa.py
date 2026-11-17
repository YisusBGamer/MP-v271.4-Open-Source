# Portal script next_dunamonpa.
# Grand Athenaeum: Full-force breakthrough: on to the next part.
DEST = {
    304030100: (304030200, 0),
    304030200: (304030300, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
