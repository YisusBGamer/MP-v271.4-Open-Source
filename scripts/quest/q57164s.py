# 57164 - Hayato 4th job advancement, with Takeda Shingen (Quest.wz start NPC 9130000).
# Advances 4111 -> 4112 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 57164
NPC = 9130000
FROM_JOB = 4111
TO_JOB = 4112

if chr.getJob() == FROM_JOB:
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.setSpeakerID(NPC)
        sm.sendSayOkay("4th Hayato Job Advancement completed!")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
