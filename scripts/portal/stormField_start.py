# Portal script stormField_start.
# Grand Athenaeum: Stormy Forest (Episode I): onto the White Mage's trail.
DEST = {
    302050030: (302050100, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
