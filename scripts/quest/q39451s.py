# 39451 - Kain 2nd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 6300 -> 6310 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 39451
FROM_JOB = 6300
TO_JOB = 6310

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Fierce malice flows through your entire body.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bKain (2nd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
