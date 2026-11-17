# Cartalion (Pantheon). The Fox-Eared People (36328): back to the Specter Battleground if the Specters still block the
# way (after dying or leaving there). Otherwise one of his default lines.
QUEST = 36328
SPECTER_BATTLEGROUND = 993166250

sm.setSpeakerID(parentID)
if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "kill") != "1":
    if sm.sendAskYesNo("The Specters are still blocking the way to the edge of the barrier. Go back and break through?"):
        sm.warpInstanceIn(SPECTER_BATTLEGROUND, 0)
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    sm.sendSayOkay(line if line is not None else "Did something happen to Pantheon?")
sm.dispose()
