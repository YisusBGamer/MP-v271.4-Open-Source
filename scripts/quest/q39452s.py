# 39452 - Kain 3rd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 6310 -> 6311 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 39452
FROM_JOB = 6310
TO_JOB = 6311

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Fierce malice flows through your entire body.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bKain (3rd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
