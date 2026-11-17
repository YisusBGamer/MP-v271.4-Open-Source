# 22131 - Evan 2nd job advancement, with Mir (Quest.wz start NPC 1013000).
# Advances 2200 -> 2211 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 22131
NPC = 1013000
FROM_JOB = 2200
TO_JOB = 2211

if chr.getJob() in (FROM_JOB, 2210):
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Mir grew?! He's larger and no longer uses an eggshell as a hat... How big can dragons get, anyway?)")
    if sm.questJobAdvance(QUEST, FROM_JOB, 2210, TO_JOB):
        sm.setSpeakerID(NPC)
        sm.sendSayOkay("You have completed the #bDragon Master 2nd Job Advancement#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
