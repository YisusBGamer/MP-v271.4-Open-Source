# 35939 - Pathfinder 2nd job advancement (Quest.wz: self-start, official text from QuestInfo.img).
# Advances 301 -> 330 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 35939
FROM_JOB = 301
TO_JOB = 330

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("(The relic is changing! Focus your mind firmly on the relic and nothing else.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("(You've freed another level of the relic's power.)\r\n\r\nYou have advanced to #bPathfinder (2nd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
