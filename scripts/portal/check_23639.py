# Portal script check_23639.
# Edelstein Park: the path to the Edelstein Outskirts, for the Xenon story.
# Exposed (23639): Beryl ambushes you on the way (your own copy of the map); after beating her you go on to the
# outskirts to talk it over. Skipping Town (23641) also takes place in the outskirts.
if sm.hasQuest(23639):
    if sm.getQRValue(23719) == "1":
        sm.warp(931060011, 1)
    else:
        sm.warpInstanceIn(931060010, 1)
elif sm.hasQuest(23641):
    sm.warp(931060011, 1)
else:
    chr.chatMessage("There's no reason to head out to the outskirts right now.")
sm.dispose()
