# 36723 Maple World's Idol (start): Angelic Buster 3rd job, v265 (25826 is retired)
ESKALADE = 3000018
if chr.getJob() == 6510 and chr.getLevel() >= 60:
    sm.setSpeakerID(ESKALADE)
    sm.sendNext("Maple World is starting to know your name, #h #. Let's take it further.")
    sm.jobAdvance(6511)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
    sm.sendSayOkay("You've taken your 3rd job advancement.")
else:
    sm.dispose()
