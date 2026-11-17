# Portal script dLibrary_start.
# Grand Athenaeum: Nix's Forest (Episode II): into the Forest of Beginnings.
DEST = {
    303010000: (303010100, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
