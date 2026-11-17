# Portal script roamingForest.
# Grand Athenaeum: Forest of Peace (Episode I): into the bat-flock fields.
DEST = {
    302030100: (302030200, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
