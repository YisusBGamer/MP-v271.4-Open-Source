# 39552 - Hoyoung 2nd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 16400 -> 16410 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 39552
FROM_JOB = 16400
TO_JOB = 16410

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You've done well to master the magic you've been using, and by now you're starting to think you need to learn some new tricks.)")
    sm.sendNext("(You remember there's another, more advanced spell book you snuck out before you left the hermitage. You should leaf through it and pick out some cool-sounding spells to add to your repertoire.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("(You learned some magic with the help of your Master's spell book. Now you've got some new spells rattling around inside of your head, waiting for you to try them out.)\r\n\r\nYou have advanced to #bHoyoung (2nd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
