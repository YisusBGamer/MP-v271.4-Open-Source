# Portal script bookEreb_done.
# Grand Athenaeum: Protecting Ereve: done, back to Cygnus's Rest.
DEST = {
    303050200: (303000000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
