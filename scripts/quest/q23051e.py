# 23051 - Black Wings' New Weapon (Mechanic)
# This only finishes the quest and brings you back. The 4th job comes from the next quest, "You Surpass Me" (23054),
# whose start script gives it.

sm.setSpeakerID(2159112) # Injured Checky
if sm.completeQuest(23051):
    sm.warp(310000000)
else:
    sm.sendSayOkay("You haven't destroyed the Black Wings' new weapon yet.")
