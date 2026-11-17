# Portal script HW_portal_tumb: Haunted Mansion Entrance -> Abandoned Graveyard.
# During [Halloween] Episode 3: The Newspaper the graveyard with the Broken Carriage opens instead.
DEST = {
    (229010000, 3): (229010100, 1),  # Haunted Mansion / Haunted Mansion Entrance -> Haunted Mansion / Abandoned Graveyard (reverse link)
}

if sm.getFieldID() == 229010000 and sm.hasQuest(15121) and not sm.hasItem(4034715):
    sm.warp(922900400)
else:
    dest = DEST.get((sm.getFieldID(), parentID))
    if dest is not None:
        sm.warp(dest[0], dest[1])
sm.dispose()
