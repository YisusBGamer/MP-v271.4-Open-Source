# Portal script enterSDI.
# Frog House (Ludibrium): the portal Ibech opens to Turtle Island (Slumbering Dragon Island), for the Secret
# Organization's Fifth Mission (22588) and what follows (22589-22591, Evan).
if any(sm.hasQuest(q) for q in (22588, 22589, 22590, 22591)):
    sm.warp(914100010, 1)
else:
    chr.chatMessage("The portal isn't open.")
sm.dispose()
