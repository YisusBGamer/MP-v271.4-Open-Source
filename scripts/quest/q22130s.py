# 22130 - Evan 1st job advancement, with Mir (Quest.wz start NPC 1013000).
# Advances 2001 -> 2200 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 22130
NPC = 1013000
FROM_JOB = 2001
TO_JOB = 2200

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(After dreaming about meeting a dragon, you stumbled across a Dragon Egg in the real world. Eventually, a baby dragon named Mir hatched from the egg.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.setSpeakerID(NPC)
        sm.sendSayOkay("You have completed the #bDragon Master 1st Job Advancement#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
