# Portal script enter_eastborder.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (400010000, 2): (400010200, 2),  # Pantheon Sanctum / East Sanctum -> East Pantheon / East Pantheon Border (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
# Grandis at War! (32250): this is the Eastern Border Rondo wanted to see. 32250 waits for record 32364 = "1".
if dest is not None and sm.hasQuest(32250) and sm.getQRValue(32364) != "1":
    sm.startQuest(32364)
    sm.setQRValue(32364, "1")
    sm.chatScript("The war barrier blocks the way east. There's no getting through to the village. Go back to Rondo in Pantheon.")
# Xenon on An Outside Perspective 1 (23654): Fenelle asked for a patrol of the eastern barrier; reaching it counts
# (record 23715 = "1"). The walk on is unchanged.
if dest is not None and sm.hasQuest(23654) and sm.getQRValue(23715) != "1":
    sm.startQuest(23715)
    sm.setQRValue(23715, "1")
    sm.chatScript("You patrolled the eastern barrier and spotted suspicious activity. Report back to Fenelle.")
# Kaiser on Protective Shield Patrol (25749): Magnus's preliminary attack hits the East Pantheon Border (940000140,
# field/event_25749.py), in a private copy; its west00 leads back here.
if dest is not None and sm.hasQuest(25749) and sm.getQRValue(25796) != "1":
    sm.warpInstanceIn(940000140, 0)
elif dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
