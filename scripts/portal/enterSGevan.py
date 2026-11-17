# Portal script enterSGevan.
# Edelstein Park 3: the Secluded Corner in the Park, where the Resistance member waits, for Evan's
# IF we really do have the same enemy. (22613) and With More People (22614).
if sm.hasQuest(22613) or sm.hasQuest(22614):
    sm.warp(931050710, 1)
else:
    chr.chatMessage("Just a quiet corner of the park.")
sm.dispose()
