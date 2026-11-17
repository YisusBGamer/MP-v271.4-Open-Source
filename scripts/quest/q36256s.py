# 36256 - Lara 2nd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 16200 -> 16210 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 36256
FROM_JOB = 16200
TO_JOB = 16210

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You've grown closer to the land spirits.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bLara (2nd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
