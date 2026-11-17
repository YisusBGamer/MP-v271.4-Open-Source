# 22132 - Evan 3rd job advancement, with Mir (Quest.wz start NPC 1013000).
# Advances 2211 -> 2214 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 22132
NPC = 1013000
FROM_JOB = 2211
TO_JOB = 2214

if chr.getJob() in (FROM_JOB, 2212):
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Mir grew again! Mir's not alone in getting stronger, you've grown as well.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, 2212, TO_JOB):
        sm.setSpeakerID(NPC)
        sm.sendSayOkay("You have completed the #bDragon Master 3rd Job Advancement#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
