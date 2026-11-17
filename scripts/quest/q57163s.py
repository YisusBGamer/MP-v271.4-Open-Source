# 57163 - Hayato 3rd job advancement, with Takeda Shingen (Quest.wz start NPC 9130000).
# Advances 4110 -> 4111 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 57163
NPC = 9130000
FROM_JOB = 4110
TO_JOB = 4111

if chr.getJob() == FROM_JOB:
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.setSpeakerID(NPC)
        sm.sendSayOkay("Hayato's 3rd Job Advancement is complete!")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
