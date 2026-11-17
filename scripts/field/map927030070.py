# 927030070 Black Mage's Temple (Shade). The Broken Seal (38068, needs its record = impossible to start, clear to
# hand in at the Broken Seal Stone) starts here: defeat the Master Omen (clear_927030070). Handing it in moves you to
# the Three Doors. The map has no exit, so anyone else goes straight back there.
MASTER_OMEN = 9300875

if sm.hasQuestCompleted(38067) and not sm.hasQuest(38068) and not sm.hasQuestCompleted(38068):
    sm.startQuestNoCheck(38068)
    sm.setQRValue(38068, "impossible")
if sm.hasQuest(38068):
    if sm.getQRValue(38068) != "clear" and not sm.hasMobsInField():
        sm.spawnMob(MASTER_OMEN, 400, -78, False)
        sm.chatScript("Defeat the Master Omen and go see the Seal Stone of Time.")
else:
    sm.warp(270000000, 7)  # in02, the Gate of the Present
