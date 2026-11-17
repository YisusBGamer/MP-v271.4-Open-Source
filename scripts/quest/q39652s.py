# 39652 - Adele 2nd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 15100 -> 15110 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 39652
FROM_JOB = 15100
TO_JOB = 15110

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(It seems the voice of the Knight Statue is calling you.)")
    sm.sendNext("(A mysterious light awoke the power sealed deep within your soul.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bAdele (2nd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
