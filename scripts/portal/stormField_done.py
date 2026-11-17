# Portal script stormField_done.
# Grand Athenaeum: White Mage's Trail: the Final Forest.
DEST = {
    302050300: (302050400, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
