# 36257 - Lara 3rd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 16210 -> 16211 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 36257
FROM_JOB = 16210
TO_JOB = 16211

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You've grown closer to the land spirits.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bLara (3rd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
