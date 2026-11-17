# Portal script enter_westST.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (400020100, 4): (400020000, 1),  # West Pantheon / West Pantheon Plains -> Pantheon Sanctum / West Sanctum (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
# Angelic Buster on West Sanctum Security (25857) sees something strange on the way (Near West Sanctum, 940010040,
# whose recorded scene exists).
if dest is not None and sm.hasQuest(25857) and sm.getQRValue(25914) != "1":
    dest = (940010040, 0)
# Kaiser on What About the West Sanctum? (25743) finds it already ransacked (West Sanctum, 940000100, event_25743).
elif dest is not None and sm.hasQuest(25743) and sm.getQRValue(25804) != "1":
    dest = (940000100, 0)
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
