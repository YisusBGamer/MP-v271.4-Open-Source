# Portal script sneak2_out.
# Grand Athenaeum: Patrol surveillance area: back to the prison.
DEST = {
    304020400: (304020010, 0),
}
dest = DEST.get(sm.getFieldID())
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
