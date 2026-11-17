# Portal script HW_prtal_frame: Second Floor -> Frame Room.
# During [Halloween] Episode 3: The Faceless Portrait the Portrait Gallery opens instead.
DEST = {
    (229000200, 3): (229000211, 2),  # Haunted Mansion / Second Floor -> Haunted Mansion / Frame Room (reverse link)
}

if sm.getFieldID() == 229000200 and sm.hasQuest(15124) and sm.getQRValue(15138, "search") != "1":
    sm.warp(922900300)
else:
    dest = DEST.get((sm.getFieldID(), parentID))
    if dest is not None:
        sm.warp(dest[0], dest[1])
sm.dispose()
