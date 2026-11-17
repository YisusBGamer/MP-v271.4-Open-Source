# Portal script DLep4_01_next.
# Grand Athenaeum: Path to the castle interior: on to the next part.
DEST = {
    306010400: (306010500, 3),
    306010500: (306010600, 1),
    306010600: (306010700, 2),
    306010700: (306010800, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
