# Portal script dLibrary_done.
# Grand Athenaeum: Forest of Beginnings: done, back to Nix's Forest.
DEST = {
    303010300: (303010000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
