# Sacred Tree Summit (993217874), own copy, during [Carcion] Last Chance (38699): the story Limbo; his defeat sets the
# quest's "boss=clear" record and ends it, then back to the Sacred Tree Summit
LIMBO = 8645436
QUEST = 38699
SUMMIT = 410007609

if chr.getInstance() is not None and sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "boss") != "clear":
    if not sm.hasMobById(LIMBO):
        sm.spawnMob(LIMBO, 300, 754, False)
    sm.chatScript("Limbo has absorbed Keira's power. Defeat Limbo!")
    sm.waitForMobDeath(LIMBO)
    sm.setQRValue(QUEST, "boss", "clear")
    sm.completeQuestNoCheck(QUEST)
    sm.chatScript("With Keira's help, Limbo is down, and his self-destruct was stopped. Now to question him.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", SUMMIT, 0)
elif chr.getInstance() is not None:
    sm.warpInstanceOut(SUMMIT, 0)
sm.dispose()
