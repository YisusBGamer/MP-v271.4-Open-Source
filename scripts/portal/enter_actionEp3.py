# Portal script enter_actionEp3.
# Grand Athenaeum: Gelimer's lab: into the danger zone.
DEST = {
    304010000: (304010300, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
