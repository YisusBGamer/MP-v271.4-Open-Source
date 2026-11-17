# Portal script infiltration00.
# Grand Athenaeum: Gelimer's lab (Episode III): into the surveillance area.
DEST = {
    304000000: (304000100, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
