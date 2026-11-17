# 38398 [Shangri-La] Last Remaining Season - start (Master Tai Yu, Chrysanthemum-Blooming Autumn). Kaling (8880852)
# appears in Bamboo-Resting Winter; it spawns nowhere in the data, so the fight is in a private copy of Winter.
# Autumn's right portal (portal/pt_410007024) starts it again if you leave.
TAI_YU = 3006162
KALING = 8880852
WINTER = 410007024

sm.setSpeakerID(TAI_YU)
sm.setBoxChat()
sm.sendNext("Hundun has fallen, but spring, summer and autumn are all broken. Only #bwinter#k still holds.")
sm.sendNext("If the winter barrier falls, Shangri-La falls with it.")
if sm.sendAskAccept("Go to #b#m410007024##k and protect the last barrier. I will follow as soon as I can."):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID) and not sm.isComplete(parentID):
        sm.warpInstanceIn(WINTER, 1)
        sm.setInstanceTime(1800, WINTER)
        sm.spawnMob(KALING, 500, 64, False)
        sm.chatScript("Kaling appears! Defeat it.")
sm.dispose()
