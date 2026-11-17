# 23050 - Black Wings' New Weapon (Wild Hunter)
# This only finishes the quest and brings you back. The 4th job comes from the next quest, "You Surpass Me" (23053),
# whose start script gives it.

sm.setSpeakerID(2159110)
if sm.completeQuest(23050):
    sm.warp(310000000)
else:
    sm.sendSayOkay("You haven't destroyed the Black Wings' new weapon yet.")
