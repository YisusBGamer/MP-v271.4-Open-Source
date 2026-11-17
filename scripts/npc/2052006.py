# Dr. Kim (Omega Sector Command Center 221000300). The Silo (221000201), where Porter and the shrunken M-Forcers are,
# has no portal from town: Dr. Kim sends you there during Gather the M-Forcers (3476) and Miniature M-Force (3477).
SILO = 221000201

sm.setSpeakerID(parentID)
if (sm.hasQuest(3476) or sm.hasQuest(3477) or (sm.hasQuestCompleted(3476) and not sm.hasQuestCompleted(3477))):
    if sm.sendAskYesNo("The M-Forcers are still in the #b#m221000201##k. Shall I send you there?"):
        sm.warp(SILO, 0)
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    sm.sendSayOkay(line if line is not None else "The Omega Sector never sleeps.")
sm.dispose()
