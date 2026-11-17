# Portal script bookEreb_next.
# Grand Athenaeum: Protecting Ereve (Episode II): on to the next part.
DEST = {
    303050000: (303050100, 0),
    303050100: (303050200, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
