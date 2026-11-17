# 39553 - Hoyoung 3rd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 16410 -> 16411 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 39553
FROM_JOB = 16410
TO_JOB = 16411

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You feel like you're lacking a certain something these days, and that usually means the time has come for you to learn some new magic.)")
    sm.sendNext("(You brought book of spells from the hermitage you visited in your dreams. You're not sure how that works, but why not page through it and see which spells catch your interest?)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("(You learned some magic with the help of your Master's spell book. Now you've got some new spells rattling around inside of your head, waiting for you to try them out.)\r\n\r\nYou have advanced to #bHoyoung (3rd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
