# 100000200 - Henesys Park -> Pet-Walking Road
# During the Cygnus Knights' "Pia's Testimony" (20904) Pet-Walking Road is "filled with Horny Mushrooms": the quest's
# monsters (Weirdo Horny Mushroom, 9300737) only live in the quest's copy of the road (910050100), so the portal leads
# there during the quest.
PIAS_TESTIMONY = 20904
if sm.hasQuest(PIAS_TESTIMONY):
    sm.warp(910050100, 9)  # its out00
else:
    sm.warp(100000202, 5)
sm.dispose()
