# Portal script DLep4_02_next.
# Grand Athenaeum: Desolate Moor escapeway: on to the next part.
DEST = {
    306020100: (306020200, 0),
    306020200: (306020300, 0),
    306020300: (306020400, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
