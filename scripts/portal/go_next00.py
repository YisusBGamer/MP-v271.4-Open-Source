# Portal script go_next00.
# Grand Athenaeum: Danger zone: through to the Black Wings prison.
DEST = {
    304010300: (304020000, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
