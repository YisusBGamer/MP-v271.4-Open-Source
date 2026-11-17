# Portal script HW_portal_top: Main Room -> Attic.
# During [Halloween] Episode 2: Dress Up the Attic with Ludmilla's dress crate opens instead.
DEST = {
    (229000210, 2): (229000213, 1),  # Haunted Mansion / Main Room -> Haunted Mansion / Attic (reverse link)
}

if sm.getFieldID() == 229000210 and sm.hasQuest(15113) and not sm.hasItem(4034707):
    sm.warp(922900200)
else:
    dest = DEST.get((sm.getFieldID(), parentID))
    if dest is not None:
        sm.warp(dest[0], dest[1])
sm.dispose()
