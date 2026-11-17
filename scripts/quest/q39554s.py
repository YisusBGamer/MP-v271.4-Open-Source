# 39554 - Hoyoung 4th job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 16411 -> 16412 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 39554
FROM_JOB = 16411
TO_JOB = 16412

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You feel like you're lacking a certain something these days, and that usually means the time has come for you to learn some new magic.)")
    sm.sendNext("(In your dream, you met with your Master and learned some new magic. The brief encounter reminded you of how you've come to miss the company of the man who raised you.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("(You met with your Master in your dream and learned new magical arts. Now you've got some new spells rattling around inside of your head, waiting for you to try them out.)\r\n\r\nYou have advanced to #bHoyoung (4th job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
