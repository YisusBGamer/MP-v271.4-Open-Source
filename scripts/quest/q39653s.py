# 39653 - Adele 3rd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 15110 -> 15111 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 39653
FROM_JOB = 15110
TO_JOB = 15111

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(It seems the voice of the Knight Statue is calling you.)")
    sm.sendNext("(A mysterious light awoke the power sealed deep within your soul.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bAdele (3rd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
