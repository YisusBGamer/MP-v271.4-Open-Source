# 23168 - Black Wings' New Weapon (Blaster)
# This only finishes the quest and brings you back. The 4th job comes from the next quest, "The Student Becomes the
# Master" (23169), whose start script gives it.

sm.setSpeakerID(2159488) # Injured Elex
if sm.completeQuest(23168):
    sm.warp(310000000)
else:
    sm.sendSayOkay("You haven't destroyed the Black Wings' new weapon yet.")
