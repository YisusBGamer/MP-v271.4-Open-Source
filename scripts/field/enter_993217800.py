# Somewhere in the Command Room (993217800), own copy, during [Arteria] Wrath (38651): the Apostle Reina; her defeat
# sets the quest's "dir" record and ends it, then back to the Command Room
REINA = 8645416
QUEST = 38651
COMMAND_ROOM = 410007545

if chr.getInstance() is not None and sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "dir") != "1":
    if not sm.hasMobById(REINA):
        sm.spawnMob(REINA, 300, 272, False)
    sm.chatScript("Defeat the Apostle Reina!")
    sm.waitForMobDeath(REINA)
    sm.setQRValue(QUEST, "dir", "1")
    sm.completeQuestNoCheck(QUEST)
    sm.chatScript("Reina is cornered. This is where it ends.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", COMMAND_ROOM, 0)
elif chr.getInstance() is not None:
    sm.warpInstanceOut(COMMAND_ROOM, 0)
sm.dispose()
