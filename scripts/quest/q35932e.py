# 35932 - Pathfinder 4th job advancement: Relic's Guidance - Aquarium. Completed in Aquarium (the quest's name):
# advances 331 -> 332 with the advancement SP/AP (sm.questJobAdvance).
QUEST = 35932
FROM_JOB = 331
TO_JOB = 332
sm.setPlayerAsSpeaker()
if chr.getJob() == FROM_JOB and chr.getFieldID() // 1000000 != 230:
    sm.sendSayOkay("(The relic's light is leading you toward Aquarium.)")
elif chr.getJob() in (FROM_JOB, TO_JOB):
    sm.sendNext("(The relic is changing! Focus your mind firmly on the relic and nothing else.)")
    if sm.questJobAdvance(QUEST, FROM_JOB, TO_JOB):
        sm.sendSayOkay("(You've finally proven your ability and become the rightful owner of the relic.)\r\n\r\nYou have advanced to #bPathfinder (4th job)#k.")
sm.dispose()
