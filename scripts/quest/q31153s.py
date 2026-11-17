# 31153 [Knight Stronghold] Stop Cygnus! - start (Chief Alex, Henesys Ruins 271010000). "Completed upon defeating
# Cygnus (Story)": the story version of the corrupted Empress (9834288) spawns nowhere in the data, so the quest takes
# you to a private copy of the Cygnus Rear Garden (271040210) to face her. Handed in to Chief Alex.
STORY_CYGNUS = 9834288
GARDEN = 271040210
HENESYS_RUINS = 271010000

if sm.questStartDialogue(parentID, 2142001):
    if sm.sendAskYesNo("#b(Go to the Knight Stronghold and face the corrupted Cygnus now?)#k"):
        sm.warpInstanceIn(GARDEN, 0)
        sm.setInstanceTime(1800, HENESYS_RUINS)
        sm.spawnMob(STORY_CYGNUS, 0, 10, False)
        sm.chatScript("Defeat the corrupted Cygnus! (Return to Chief Alex in the Henesys Ruins afterwards.)")
sm.dispose()
