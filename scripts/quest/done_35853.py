# [Destiny Weapon] A Transcendent's Weapon (35853) done: the Destiny Weapon window opens with The Adversary's
# Determination (35858), which keeps the battles' clear/point record (GenesisLiberation credits it on each boss).
TRACKER = 35858

if not sm.hasQuest(TRACKER) and not sm.hasQuestCompleted(TRACKER):
    sm.startQuestNoCheck(TRACKER)
    sm.setQRValue(TRACKER, "start", "1")
    sm.chatScript("[Destiny Weapon] The Adversary's Determination has begun. Defeat Hard Chosen Seren first.")
sm.dispose()
