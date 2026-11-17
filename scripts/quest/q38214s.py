# 38214 [Karote] Kalos the Guardian - start (Neinheart, Karote Summit 410005005). T-Boy fights with Kalos's stolen
# power: the occupied Kalos (8880820) spawns nowhere in the data, so the fight is in a private copy of the Summit.
# Handed in to Neinheart.
OCCUPIED_KALOS = 8880820
SUMMIT = 410005005

if sm.questStartDialogue(parentID, 3005235):
    if sm.sendAskYesNo("#b(Face T-Boy and the occupied Kalos now?)#k"):
        sm.warpInstanceIn(SUMMIT, 0)
        sm.setInstanceTime(1800, SUMMIT)
        sm.spawnMob(OCCUPIED_KALOS, 300, 371, False)
        sm.chatScript("Neutralize Kalos and defeat T-Boy!")
sm.dispose()
