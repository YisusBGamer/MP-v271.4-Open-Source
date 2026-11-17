# Portal script DLep4_01_Out.
# Grand Athenaeum: Path to the castle interior: back to the tower.
DEST = {
    306010400: (306010200, 3),
    306010500: (306010200, 3),
    306010600: (306010200, 3),
    306010700: (306010200, 3),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
