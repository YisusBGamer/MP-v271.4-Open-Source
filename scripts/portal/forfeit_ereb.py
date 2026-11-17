# Portal script forfeit_ereb.
# Grand Athenaeum: Retaking Ereve: give up and go back.
DEST = {
    304060100: (304060000, 0),
    304060200: (304060000, 0),
    304060300: (304060000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
