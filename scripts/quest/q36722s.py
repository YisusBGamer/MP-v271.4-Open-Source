# 36722 The Nova's New Hero (start): Angelic Buster 2nd job, v265 (25825 is retired)
ESKALADE = 3000018
if chr.getJob() == 6500 and chr.getLevel() >= 30:
    sm.setSpeakerID(ESKALADE)
    sm.sendNext("Our pact has grown stronger, #h #. Ready for more of my power?")
    sm.jobAdvance(6510)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
    sm.giveNewSecondary(1352602)
    sm.sendSayOkay("Now focus! You're an Angelic Buster for real now.")
else:
    sm.dispose()
