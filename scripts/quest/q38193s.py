# 38193 [Job Advancement] Tears of a Fox - Shade 4th job advancement (from the quest list).
# Advances 2511 -> 2512 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 38193
FROM_JOB = 2511
TO_JOB = 2512

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("#b(Seeing Moonbeam again made you more determined than ever to regain your power. You strengthen your bond with the spirits, drawing on all your training.)#k")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bShade (4th job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
