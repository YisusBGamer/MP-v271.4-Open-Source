# Portal script move_curesedEreb.
# Grand Athenaeum: Road to Ereve: into the cursed Ereve.
DEST = {
    304050100: (304060000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
