# Portal script done_ereb.
# Grand Athenaeum: Retaking Ereve: to the Calcified Empress.
DEST = {
    304060300: (304070000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
