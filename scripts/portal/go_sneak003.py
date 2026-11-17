# Portal script go_sneak003.
# Grand Athenaeum: Mine elevator: sneak past the patrol.
DEST = {
    304020300: (304020400, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
