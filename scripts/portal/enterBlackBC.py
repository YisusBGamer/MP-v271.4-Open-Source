# Portal script enterBlackBC.
# Frog House (Ludibrium): the portal Ibech opens for the Secret Organization's missions (Evan):
#   Third Mission (22582): the Spirits' Playground, where the Secret Master Soul Teddies are.
#   Releasing the Free Spirits (22583): the Sky Terrace (climb to the top).
#   Eliminating Door Blocks (22584): the safe's terrace (climb up and go in).
if sm.hasQuest(22582):
    sm.warp(922030002, 1)
elif sm.hasQuest(22583):
    sm.warp(922030010, 1)
elif sm.hasQuest(22584):
    sm.warp(922030020, 1)
else:
    chr.chatMessage("The portal isn't open.")
sm.dispose()
