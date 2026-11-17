# 38180 [Job Advancement] The Heart of a Fox - Shade 2nd job advancement (Silver, Fox Point Village).
# Advances 2500 -> 2510 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 38180
FROM_JOB = 2500
TO_JOB = 2510

if chr.getJob() == FROM_JOB:
    sm.setSpeakerID(3002005)
    sm.sendNext("So you're leaving us, Shade. Gather your belongings... not that you have many.")
    sm.sendSay("Before you go, take some of our power with you. Every fox in the village wants to help.")
    sm.setPlayerAsSpeaker()
    sm.sendNext("#b(The foxes send you off and lend you some of their power. You promise to return. Moonbeam never appeared...)#k")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bShade (2nd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
