# 2159453 Lilin in Rien (140020300). Finding Aran 1-3 (Shade 38047-38049) and Reunion with Aran (38050) are played on
# Riena Strait (914050001 / 914050007): Lilin's boat takes you back there while they're unfinished.
RIENA_STRAIT = 914050001

if sm.hasQuest(38047) or (sm.hasQuestCompleted(38047) and not sm.hasQuestCompleted(38050)):
    if sm.sendAskYesNo("The boat from #rPuro#k is still here. Do you want to go to Riena Strait?"):
        if sm.hasQuest(38047):
            sm.setQRValue(38047, "clear", "clear")
            sm.completeQuest(38047)
        sm.warp(RIENA_STRAIT, 0)
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    if line is not None:
        sm.sendSayOkay(line)
sm.dispose()
