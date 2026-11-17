# Portal script enter_erebmonpa.
# Grand Athenaeum: Cursed Ereve: retake Ereve.
DEST = {
    304060000: (304060100, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
