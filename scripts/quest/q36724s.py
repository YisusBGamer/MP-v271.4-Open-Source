# 36724 I Like Pink Too (start): Angelic Buster 4th job, v265 (25827 is retired)
ESKALADE = 3000018
if chr.getJob() == 6511 and chr.getLevel() >= 100:
    sm.setSpeakerID(ESKALADE)
    sm.sendNext("You've mastered everything I've given you, #h #. Here's the rest.")
    sm.jobAdvance(6512)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
    sm.sendSayOkay("You've taken your 4th job advancement.")
else:
    sm.dispose()
