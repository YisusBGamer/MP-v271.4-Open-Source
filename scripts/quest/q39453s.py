# 39453 - Kain 4th job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 6311 -> 6312 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 39453
FROM_JOB = 6311
TO_JOB = 6312

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Fierce malice flows through your entire body.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bKain (4th job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
