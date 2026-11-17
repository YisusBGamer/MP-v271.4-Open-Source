# 100040000 Golem's Temple Entrance, scr00 (the door with the puppet on top, at the east end of the map):
# - the Puppeteer's hideout, for Traces of the Puppeteer (Aran 21734)
# - the Black Wings' old meeting room, for Luminous' ritual: The Ceremony (25555) and Secret Ritual (25584).
#   Entering it sets the record the quest checks (enter_q25555 / enter_q25584).
# - the Abandoned Hideout Entrance, for Xenon's Black Wings on the Move (23676) / The Secrets of the Puppeteer (23677)
if sm.hasQuest(21734):
    sm.warpInstanceIn(910510202, 0)
elif sm.hasQuest(25555):
    sm.warpInstanceIn(910600200, 0)
elif sm.hasQuest(25584):
    sm.warpInstanceIn(910600201, 0)
elif sm.hasQuest(23676) and not sm.hasQuestCompleted(23677):
    # Black Wings on the Move (Xenon): the Abandoned Hideout Entrance, whose out00 leads back to this door
    sm.warp(910600300, 0)
else:
    sm.chat("There is nothing to see here.")
sm.dispose()
