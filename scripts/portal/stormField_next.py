# Portal script stormField_next.
# Grand Athenaeum: White Mage's Trail: on to the next part.
DEST = {
    302050100: (302050200, 0),
    302050200: (302050300, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
