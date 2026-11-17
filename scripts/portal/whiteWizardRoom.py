# Portal script whiteWizardRoom.
# Grand Athenaeum: Aurora Great Temple lobby: the White Mage's lab.
DEST = {
    302040000: (302040020, 1),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
