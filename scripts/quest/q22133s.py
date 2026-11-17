# 22133 - Evan 4th job advancement, with Mir (Quest.wz start NPC 1013000).
# Advances 2214 -> 2217 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 22133
NPC = 1013000
FROM_JOB = 2214
TO_JOB = 2217

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Mir is now fully grown. Is this the true form of the Onyx Dragon? His overwhelming presence is negated by your close relationship. Maybe that's part of being a Dragon Master.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.setSpeakerID(NPC)
        sm.sendSayOkay("You have completed the #bDragon Master 4th Job Advancement#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
