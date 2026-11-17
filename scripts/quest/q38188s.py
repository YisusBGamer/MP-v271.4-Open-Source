# 38188 [Job Advancement] Blessings of the Fox God - Shade 3rd job advancement (from the quest list).
# Advances 2510 -> 2511 with the advancement SP/AP and completes the quest (sm.questJobAdvance).
QUEST = 38188
FROM_JOB = 2510
TO_JOB = 2511

if chr.getJob() == FROM_JOB:
    sm.setPlayerAsSpeaker()
    sm.sendNext("#b(You can feel the Fox God's blessing around you growing stronger. You try to deepen your bond with the spirits using this power.)#k")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("You have advanced to #bShade (3rd job)#k.")
elif chr.getJob() == TO_JOB and not sm.hasQuestCompleted(QUEST):
    sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB)
sm.dispose()
