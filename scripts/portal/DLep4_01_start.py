# Portal script DLep4_01_start.
# Grand Athenaeum: Lion King's Castle tower (Episode IV): the path to the castle interior.
DEST = {
    306010200: (306010400, 1),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
