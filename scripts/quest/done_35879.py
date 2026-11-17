# [Destiny Weapon] A New Level of Mastery (35879) done: the second stage opens with Adversary's Conviction (35878),
# which keeps the second stage's Battle clear/point record (GenesisLiberation credits it on each boss).
TRACKER = 35878

if not sm.hasQuest(TRACKER) and not sm.hasQuestCompleted(TRACKER):
    sm.startQuestNoCheck(TRACKER)
    sm.setQRValue(TRACKER, "start", "1")
    sm.chatScript("[Destiny Weapon] Adversary's Conviction has begun. Defeat the Hard First Adversary first.")
sm.dispose()
