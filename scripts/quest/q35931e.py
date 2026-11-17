# 35931 - Pathfinder 3rd job advancement: Relic's Guidance - Perion. Completed in Perion (the quest's name):
# advances 330 -> 331 with the advancement SP/AP (sm.questJobAdvance).
QUEST = 35931
FROM_JOB = 330
TO_JOB = 331
sm.setPlayerAsSpeaker()
if chr.getJob() == FROM_JOB and chr.getFieldID() // 1000000 != 102:
    sm.sendSayOkay("(The relic's light is leading you toward Perion.)")
elif chr.getJob() in (FROM_JOB, TO_JOB):
    sm.sendNext("(The relic is changing! Focus your mind firmly on the relic and nothing else.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("(You've freed another level of the relic's power.)\r\n\r\nYou have advanced to #bPathfinder (3rd job)#k.")
sm.dispose()
